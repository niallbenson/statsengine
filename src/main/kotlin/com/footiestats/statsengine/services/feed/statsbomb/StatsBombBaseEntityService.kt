package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.MatchLineup
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombDateUtils
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombBaseEntityService(
        private val sourceRepository: SourceRepository,
        private val competitionRepository: CompetitionRepository,
        private val countryRepository: CountryRepository,
        private val seasonRepository: SeasonRepository,
        private val competitionSeasonRepository: CompetitionSeasonRepository,
        private val matchRepository: MatchRepository,
        private val teamRepository: TeamRepository,
        private val managerRepository: ManagerRepository,
        private val matchMetadataRepository: MatchMetadataRepository,
        private val competitionStageRepository: CompetitionStageRepository,
        private val stadiumRepository: StadiumRepository,
        private val refereeRepository: RefereeRepository,
        private val matchLineupRepository: MatchLineupRepository,
        private val playerRepository: PlayerRepository,
        private val lineupPlayerRepository: LineupPlayerRepository
) {
    // Source
    fun getStatsBombSource() = sourceRepository.findByName("StatsBomb")
            ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

    // Competition
    fun save(competition: Competition) = competitionRepository.save(competition)

    fun getCompetitionsBySource(source: Source) = competitionRepository.findAllBySource(source)
    fun getCompetitionByExternalId(id: String) = competitionRepository.findBySourceExternalId(id)
    fun getOrCreateCompetition(statsBombCompetition: StatsBombCompetition): Competition {
        var competition =
                competitionRepository.findBySourceExternalId(statsBombCompetition.competitionId.toString())

        if (competition == null) {
            competition = createCompetition(statsBombCompetition)
        } else {
            competition = checkAndUpdateCompetition(competition, statsBombCompetition)
        }
        return competition
    }

    private fun createCompetition(statsBombCompetition: StatsBombCompetition): Competition {
        val competition = mapCompetitionToEntity(statsBombCompetition)

        return competitionRepository.save(competition)
    }

    private fun mapCompetitionToEntity(statsBombCompetition: StatsBombCompetition): Competition {
        return Competition(
                statsBombCompetition.competitionName,
                Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase()),
                statsBombCompetition.competitionId.toString(),
                getOrCreateCountry(statsBombCompetition.countryName),
                getStatsBombSource())
    }

    private fun checkAndUpdateCompetition(
            competition: Competition,
            statsBombCompetition: StatsBombCompetition
    ): Competition {
        if (competition.name != statsBombCompetition.competitionName
                || competition.country.name != statsBombCompetition.countryName
                || competition.gender.name != statsBombCompetition.competitionGender) {
            competition.name = statsBombCompetition.competitionName
            competition.country = getOrCreateCountry(statsBombCompetition.countryName)
            competition.gender = Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase())

            competitionRepository.save(competition)
        }
        return competition
    }

    // Country
    fun save(country: Country) = countryRepository.save(country)

    fun getAllCountries() = countryRepository.findAll()
    fun getOrCreateCountry(name: String): Country {
        var country = countryRepository.findByName(name)

        if (country == null) {
            country = Country(name, null, getStatsBombSource())

            country = countryRepository.save(country)
        }
        return country
    }

    fun getOrCreateCountry(statsBombCountry: StatsBombCountry): Country {
        var country = countryRepository.findBySourceExternalId(statsBombCountry.id.toString())

        if (country == null) {
            country = getOrCreateCountry(statsBombCountry.name)
            country.sourceExternalId = statsBombCountry.id.toString()

            countryRepository.save(country)
        } else {
            if (country.name != statsBombCountry.name) {
                country.name = statsBombCountry.name

                countryRepository.save(country)
            }
        }
        return country
    }

    // Season
    fun save(season: Season) = seasonRepository.save(season)

    fun getSeasonsBySource(source: Source) = seasonRepository.findBySource(source)
    fun getSeasonByExternalId(id: String) = seasonRepository.findBySourceExternalId(id)

    fun getOrCreateSeason(externalSeasonId: String, seasonName: String): Season {
        var season = seasonRepository.findBySourceExternalId(externalSeasonId)

        if (season == null) {
            season = Season(
                    seasonName,
                    externalSeasonId,
                    getStatsBombSource()
            )
            seasonRepository.save(season)
        } else {
            if (season.name != seasonName) {
                season.name = seasonName

                seasonRepository.save(season)
            }
        }
        return season
    }

    // Competition Season
    fun save(competitionSeason: CompetitionSeason) = competitionSeasonRepository.save(competitionSeason)

    fun getCompetitionSeasonsForCompetitions(competitions: Iterable<Competition>) =
            competitionSeasonRepository.findAllByCompetitionIn(competitions)

    fun getCompetitionSeasons() = competitionSeasonRepository.findAllByCompetition_Source(getStatsBombSource())

    fun getOrCreateCompetitionSeason(competition: Competition, season: Season): CompetitionSeason {
        var competitionSeason =
                competitionSeasonRepository.findByCompetitionAndSeason(competition, season)

        if (competitionSeason == null) {
            competitionSeason = CompetitionSeason(competition, season)

            competitionSeasonRepository.save(competitionSeason)
        }
        return competitionSeason
    }

    // Match
    fun save(match: Match) = matchRepository.save(match)

    fun getMatchesForCompetitionSeason(competitionSeason: CompetitionSeason) =
            matchRepository.findAllByCompetitionSeason(competitionSeason)

    fun getMatchByExternalId(id: String) = matchRepository.findBySourceExternalId(id)

    // Team
    fun save(team: Team) = teamRepository.save(team)

    fun getTeamByExternalId(id: String) = teamRepository.findBySourceExternalId(id)
    fun getOrCreateTeam(statsBombTeam: StatsBombTeam): Team {
        var team = teamRepository.findBySourceExternalId(statsBombTeam.teamId.toString())

        val managers =
                if (statsBombTeam.managers != null)
                    statsBombTeam.managers.map { m -> getOrCreateManager(m) }.toCollection(arrayListOf())
                else ArrayList<Manager>()

        if (team == null) {
            team = Team(
                    statsBombTeam.teamName,
                    Gender.valueOf(statsBombTeam.teamGender.toUpperCase()),
                    statsBombTeam.teamGroup,
                    statsBombTeam.teamId.toString(),
                    getOrCreateCountry(statsBombTeam.country),
                    managers,
                    getStatsBombSource()
            )

            teamRepository.save(team)
        } else {
            if (team.name != statsBombTeam.teamName
                    || team.gender != Gender.valueOf(statsBombTeam.teamGender.toUpperCase())
                    || team.teamGroup != statsBombTeam.teamGroup
                    || team.country != getOrCreateCountry(statsBombTeam.country)
                    || !compareManagers(team.managers, managers)) {
                team.name = statsBombTeam.teamName
                team.gender = Gender.valueOf(statsBombTeam.teamGender.toUpperCase())
                team.teamGroup = statsBombTeam.teamGroup
                team.country = getOrCreateCountry(statsBombTeam.country)
                team.managers = managers

                teamRepository.save(team)
            }
        }
        return team
    }

    private fun compareManagers(m1: Iterable<Manager>?, m2: Iterable<Manager>?): Boolean {
        if ((m1 == null && m2 != null) || (m1 != null && m2 == null)) return false

        if (m1 == null && m2 == null) return true

        if (m1!!.count() != m2!!.count()) {
            return false
        }

        for (m in m1) {
            if (!m2.contains(m)) return false
        }

        return true
    }

    // Manager
    fun save(manager: Manager) = managerRepository.save(manager)

    fun getOrCreateManager(statsBombManager: StatsBombManager): Manager {
        var manager = managerRepository.findBySourceExternalId(statsBombManager.id.toString())

        if (manager == null) {
            manager = Manager(
                    statsBombManager.name,
                    statsBombManager.nickname,
                    StatsBombDateUtils.convertToDateFromShort(statsBombManager.dob),
                    statsBombManager.id.toString(),
                    getOrCreateCountry(statsBombManager.country),
                    getStatsBombSource()
            )

            managerRepository.save(manager)
        } else {
            if (manager.name != statsBombManager.name
                    || manager.nickname != statsBombManager.nickname
                    || manager.dateOfBirth != StatsBombDateUtils.convertToDateFromShort(statsBombManager.dob)
                    || manager.country.sourceExternalId != statsBombManager.country.id.toString()) {
                manager.name = statsBombManager.name
                manager.nickname = statsBombManager.nickname
                manager.dateOfBirth = StatsBombDateUtils.convertToDateFromShort(statsBombManager.dob)
                manager.country = getOrCreateCountry(statsBombManager.country)

                managerRepository.save(manager)
            }
        }
        return manager
    }

    // Competition Stage
    fun save(competitionStage: CompetitionStage) = competitionStageRepository.save(competitionStage)

    fun getOrCreateCompetitionStage(statsBombCompetitionStage: StatsBombCompetitionStage): CompetitionStage {
        var competitionStage =
                competitionStageRepository.findBySourceExternalId(statsBombCompetitionStage.id.toString())

        if (competitionStage == null) {
            competitionStage = CompetitionStage(
                    statsBombCompetitionStage.name,
                    statsBombCompetitionStage.id.toString(),
                    getStatsBombSource()
            )

            competitionStageRepository.save(competitionStage)
        } else {
            if (competitionStage.name != statsBombCompetitionStage.name) {
                competitionStage.name = statsBombCompetitionStage.name

                competitionStageRepository.save(competitionStage)
            }
        }
        return competitionStage
    }

    // Stadium
    fun save(stadium: Stadium) = stadiumRepository.save(stadium)

    fun getOrCreateStadium(statsBombStadium: StatsBombStadium): Stadium {
        var stadium = stadiumRepository.findBySourceExternalId(statsBombStadium.id.toString())

        if (stadium == null) {
            stadium = Stadium(
                    statsBombStadium.name,
                    statsBombStadium.id.toString(),
                    getOrCreateCountry(statsBombStadium.country),
                    getStatsBombSource()
            )

            stadiumRepository.save(stadium)
        } else {
            if (stadium.name != statsBombStadium.name
                    || stadium.country.id != statsBombStadium.country.id) {
                stadium.name = statsBombStadium.name
                stadium.country = getOrCreateCountry(statsBombStadium.country)

                stadiumRepository.save(stadium)
            }
        }
        return stadium
    }

    // Match Metadata
    fun save(matchMetadata: MatchMetadata) = matchMetadataRepository.save(matchMetadata)

    fun getOrCreateMatchMetaData(statsBombMatchMetadata: StatsBombMatchMetadata): MatchMetadata {
        var matchMetadata = matchMetadataRepository.findByDataVersionAndShotFidelityVersionAndXyFidelityVersion(
                statsBombMatchMetadata.dataVersion,
                statsBombMatchMetadata.shotFidelityVersion,
                statsBombMatchMetadata.xyFidelityVersion)

        if (matchMetadata == null) {
            matchMetadata = MatchMetadata(
                    statsBombMatchMetadata.dataVersion,
                    statsBombMatchMetadata.shotFidelityVersion,
                    statsBombMatchMetadata.xyFidelityVersion,
                    getStatsBombSource()
            )

            matchMetadataRepository.save(matchMetadata)
        }
        return matchMetadata
    }

    // Referee
    fun save(referee: Referee) = refereeRepository.save(referee)

    fun getOrCreateReferee(statsBombReferee: StatsBombReferee): Referee {
        var referee = refereeRepository.findBySourceExternalId(statsBombReferee.id.toString())

        val country = if (statsBombReferee.country != null) getOrCreateCountry(statsBombReferee.country!!) else null

        if (referee == null) {
            referee = Referee(
                    statsBombReferee.name,
                    statsBombReferee.id.toString(),
                    country,
                    getStatsBombSource()
            )

            refereeRepository.save(referee)
        } else {
            if (referee.name != statsBombReferee.name || referee.country != country) {
                referee.name = statsBombReferee.name
                referee.country = country
            }
        }
        return referee
    }

    // Match Lineup
    fun save(matchLineup: MatchLineup) = matchLineupRepository.save(matchLineup)

    fun getOrCreateMatchLineup(match: Match, statsBombLineup: StatsBombLineup): MatchLineup {
        val team = teamRepository.findBySourceExternalId(statsBombLineup.teamId.toString())
                ?: throw StatsBombEntityNotFound("Unable to find lineup team: ${statsBombLineup.teamName}")

        var lineup = matchLineupRepository.findByMatchAndTeam(match, team)

        if (lineup == null) {
            lineup = MatchLineup(match, team)

            matchLineupRepository.save(lineup)

            statsBombLineup.players.forEach {
                val lineupPlayer = LineupPlayer(
                        it.jerseyNumber,
                        getOrCreatePlayer(
                                it.playerId.toString(),
                                it.playerName,
                                it.playerNickname,
                                it.country),
                        lineup
                )

                lineupPlayerRepository.save(lineupPlayer)
            }
        } else {
            // TODO: test whether line up players are matching, if not then update
        }
        return lineup
    }

    // Player
    fun getPlayerByExternalId(id: String) = playerRepository.findBySourceExternalId(id)
    fun getOrCreatePlayer(playerId: String, playerName: String, playerNickname: String?, country: StatsBombCountry?): Player {
        var player = playerRepository.findBySourceExternalId(playerId)

        if (player == null) {
            player = Player(
                    playerName,
                    playerNickname,
                    playerId,
                    if (country != null) getOrCreateCountry(country) else null,
                    getStatsBombSource()
            )

            playerRepository.save(player)
        } else {
            if (player.name != playerName
                    || player.nickName != playerNickname
                    || player.country?.sourceExternalId != country?.id.toString()) {
                player.name = playerName
                player.nickName = playerNickname
                if (country != null) player.country = getOrCreateCountry(country)

                playerRepository.save(player)
            }
        }
        return player
    }
}