package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.*
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
        private val metadataRepository: MetadataRepository,
        private val competitionStageRepository: CompetitionStageRepository,
        private val stadiumRepository: StadiumRepository,
        private val refereeRepository: RefereeRepository
) {

    fun getStatsBombSource() = sourceRepository.findByName("StatsBomb")
            ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

    fun getCompetitionsBySouce(source: Source) = competitionRepository.findAllBySource(source)
    fun save(competition: Competition) = competitionRepository.save(competition)

    fun getAllCountries() = countryRepository.findAll()
    fun save(country: Country) = countryRepository.save(country)

    fun getSeasonsBySource(source: Source) = seasonRepository.findBySource(source)
    fun save(season: Season) = seasonRepository.save(season)

    fun getCompetitionSeasonsForCompetitions(competitions: Iterable<Competition>) =
            competitionSeasonRepository.findAllByCompetitionIn(competitions)
    fun save(competitionSeason: CompetitionSeason) = competitionSeasonRepository.save(competitionSeason)

    fun getCompetitionSeasonsByCompetitionSource(source: Source) =
            competitionSeasonRepository.findAllByCompetitionSource(source)

    fun getMatchesForCompetitionExternalIds(ids: Iterable<String>) =
            matchRepository.findAllByCompetitionSourceExternalId(ids)


}