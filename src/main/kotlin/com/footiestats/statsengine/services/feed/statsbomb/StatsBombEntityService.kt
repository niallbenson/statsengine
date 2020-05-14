package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.dtos.statsbomb.StatsBombCountry
import com.footiestats.statsengine.dtos.statsbomb.StatsBombManager
import com.footiestats.statsengine.dtos.statsbomb.StatsBombTeam
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service
import java.util.*

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
        private val metadataRepository: MetadataRepository,
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
    fun getOrCreateSeason(name: String): Season {
        var season = seasonRepository.findByName(name)

        if (season == null) {
            season = seasonRepository.save(
                    Season(name, getStatsBombSource(), null)
            )
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

        }
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
        }
    }


}