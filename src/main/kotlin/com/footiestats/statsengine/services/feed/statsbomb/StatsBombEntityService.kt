package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service

@Service
class StatsBombEntityService(
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
        private val refereeRepository: RefereeRepository
) {

    // Source
    fun getStatsBombSource() = sourceRepository.findByName("StatsBomb")
            ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

    // Competition
    fun save(competition: Competition) = competitionRepository.save(competition)

    fun getCompetitionsBySouce(source: Source) = competitionRepository.findAllBySource(source)
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
        return competitionRepository.save(
                Competition(
                        getOrCreateCountry(statsBombCompetition.countryName),
                        statsBombCompetition.competitionName,
                        Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase()),
                        getStatsBombSource(),
                        statsBombCompetition.competitionId.toString())
        )
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
    fun getCountryByExternalId(id: Long) = countryRepository.findById(id)
    fun getOrCreateCountry(name: String): Country {
        var country = countryRepository.findByName(name)

        if (country == null) {
            country = countryRepository.save(Country(name, getStatsBombSource(), null))
        }
        return country
    }

    fun getOrCreateCountry(statsBombCountry: StatsBombCountry): Country {
        var country = countryRepository.findBySourceExternalId(statsBombCountry.id.toString())

        if (country == null) {
            country = getOrCreateCountry(statsBombCountry.name)!!
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
    fun getOrCreateSeason(statsBombSeason: StatsBombSeason): Season {
        var season = seasonRepository.findBySourceExternalId(statsBombSeason.seasonId.toString())

        if (season == null) {
            season = Season(
                    statsBombSeason.seasonName,
                    getStatsBombSource(),
                    statsBombSeason.seasonId.toString()
            )
        } else {
            if (season.name != statsBombSeason.seasonName) {
                season.name = statsBombSeason.seasonName

                seasonRepository.save(season)
            }
        }
        return season
    }
    fun getOrCreateSeason(seasonId: String, seasonName: String): Season {
        var season = seasonRepository.findBySourceExternalId(seasonId)

        if (season == null) {
            season = Season(
                    seasonName,
                    getStatsBombSource(),
                    seasonId
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

    fun getCompetitionSeasons() = competitionSeasonRepository.findAllByCompetitionSource(getStatsBombSource())

    fun getOrCreateCompetitionSeason(competition: Competition, season: Season): CompetitionSeason {
        var competitionSeason =
                competitionSeasonRepository.findByCompetitionAndSeason(competition, season)

        if (competitionSeason == null) {
            competitionSeason = competitionSeasonRepository.save(
                    CompetitionSeason(competition, season)
            )
        }
        return competitionSeason
    }

    // Match
    fun save(match: Match) = matchRepository.save(match)

    fun getMatchesForCompetitionExternalIds(ids: Iterable<String>) =
            matchRepository.findAllByCompetitionSourceExternalId(ids)

    fun getMatchByExternalId(id: String) = matchRepository.findBySourceExternalId(id)

    // Team
    fun save(team: Team) = teamRepository.save(team)

    fun getTeamByExternalId(id: String) = teamRepository.findBySourceExternalId(id)
    fun getManagerById(id: Long) = managerRepository.findById(id)
    fun getOrCreateTeam(statsBombTeam: StatsBombTeam): Team {
        var team = teamRepository.findBySourceExternalId(statsBombTeam.teamId.toString())

        if (team == null) {
            team = Team(
                    statsBombTeam.teamName,
                    Gender.valueOf(statsBombTeam.teamGender),
                    statsBombTeam.teamGroup,
                    getOrCreateCountry(statsBombTeam.country),
                    statsBombTeam.managers.map { m -> getOrCreateManager(m) }.toCollection(arrayListOf()),
                    getStatsBombSource(),
                    statsBombTeam.teamId.toString()
            )
        } else {
            if (team.name != statsBombTeam.teamName
                    || team.gender != Gender.valueOf(statsBombTeam.teamGender)
                    || team.group != statsBombTeam.teamGroup
                    || team.country != getOrCreateCountry(statsBombTeam.country)
                    || team.managers != statsBombTeam.managers.map { m -> getOrCreateManager(m) }.toCollection(arrayListOf())) {
                team.name = statsBombTeam.teamName
                team.gender = Gender.valueOf(statsBombTeam.teamGender)
                team.group = statsBombTeam.teamGroup
                team.country = getOrCreateCountry(statsBombTeam.country)
                team.managers = statsBombTeam.managers.map { m -> getOrCreateManager(m) }.toCollection(arrayListOf())

                teamRepository.save(team)
            }
        }
        return team
    }

    // Manager
    fun save(manager: Manager) = managerRepository.save(manager)

    fun getOrCreateManager(statsBombManager: StatsBombManager): Manager {
        var manager = managerRepository.findBySourceExternalId(statsBombManager.id.toString())

        if (manager == null) {
            manager = Manager(
                    statsBombManager.name,
                    statsBombManager.nickname,
                    StatsBombUtils.convertToDateFromShort(statsBombManager.dob),
                    getOrCreateCountry(statsBombManager.country),
                    getStatsBombSource(),
                    statsBombManager.id.toString()
            )
        } else {
            if (manager.name != statsBombManager.name
                    || manager.nickname != statsBombManager.nickname
                    || manager.dateOfBirth != StatsBombUtils.convertToDateFromShort(statsBombManager.dob)
                    || manager.country.id != statsBombManager.country.id) {
                manager.name = statsBombManager.name
                manager.nickname = statsBombManager.nickname
                manager.dateOfBirth = StatsBombUtils.convertToDateFromShort(statsBombManager.dob)
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
                    getStatsBombSource(),
                    statsBombCompetitionStage.id.toString()
            )
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
                    getOrCreateCountry(statsBombStadium.country),
                    getStatsBombSource(),
                    statsBombStadium.id.toString()
            )
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
        var matchMetadata = matchMetadataRepository.findByValues(
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
                    country,
                    getStatsBombSource(),
                    statsBombReferee.id.toString()
            )
        } else {
            if (referee.name != statsBombReferee.name || referee.country != country) {
                referee.name = statsBombReferee.name
                referee.country = country
            }
        }
        return referee
    }
}