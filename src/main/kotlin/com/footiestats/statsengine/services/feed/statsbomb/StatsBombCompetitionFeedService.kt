package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.entities.statsbomb.mappers.StatsBombCompetitionMapper
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI

@Service
class StatsBombCompetitionFeedService(
        private val competitionRepository: CompetitionRepository,
        private val countryRepository: CountryRepository,
        private val seasonRepository: SeasonRepository,
        private val sourceRepository: SourceRepository) {

    fun updateFromStatsBombCompetitions(): Iterable<Competition> {
        val statsBombCompetitions = getStatsBombCompetitions()

        val statsBombSource = getStatsBombSource()

        return addMissingAndReturnAll(statsBombCompetitions, statsBombSource)
    }

    private fun getStatsBombCompetitions(): Iterable<StatsBombCompetition> {
        val uri = URI("https://raw.githubusercontent.com/statsbomb/open-data/master/data/competitions.json")
        val jsonResponse = RestTemplate().getForObject<String>(uri)

        return StatsBombCompetitionMapper.fromJson(jsonResponse)
    }

    fun getStatsBombSource(): Source {
        val statsBombSource = sourceRepository.findByName("StatsBomb")
                ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

        return statsBombSource
    }

    fun addMissingAndReturnAll(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            statsBombSource: Source
    ): Iterable<Competition> {

        val seasons = processSeasons(statsBombCompetitions, statsBombSource)
        val countries = processCountries(statsBombCompetitions)

        return processCompetitions(statsBombCompetitions, countries, seasons, statsBombSource)
    }

    fun processSeasons(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            statsBombSource: Source
    ): Iterable<Season> {
        var seasons = seasonRepository.findBySource(statsBombSource)
        val seasonIdToNameMap = statsBombCompetitions.map { s -> s.seasonId to s.seasonName }.toMap()

        for (s in seasonIdToNameMap)
            seasons = processSeason(s.value, s.key.toString(), seasons, statsBombSource)

        return seasons
    }

    fun processSeason(
            seasonName: String,
            seasonId: String,
            seasons: ArrayList<Season>,
            statsBombSource: Source
    ): ArrayList<Season> {
        val season = seasons.find { s -> s.name == seasonName }
        if (season == null) {
            val newSeason = Season(seasonName, statsBombSource, seasonId)
            seasonRepository.save(newSeason)
            return seasonRepository.findBySource(statsBombSource)
        }
        return seasons
    }

    fun processCountries(statsBombCompetitions: Iterable<StatsBombCompetition>): Iterable<Country> {
        var countries = countryRepository.findAll()
        val distinctCountryNames = statsBombCompetitions.map { c -> c.countryName }.distinct()

        for (c in distinctCountryNames) countries = processCountry(c, countries)

        return countries
    }

    fun processCountry(
            countryName: String,
            countries: Iterable<Country>
    ): Iterable<Country> {
        val country = countries.find { c -> c.name == countryName }
        if (country == null) {
            val newCountry = Country(countryName)
            countryRepository.save(newCountry)
            return countryRepository.findAll()
        }
        return countries
    }

    fun processCompetitions(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            countries: Iterable<Country>,
            seasons: Iterable<Season>,
            statsBombSource: Source
    ): Iterable<Competition> {

        val competitions = competitionRepository.findAllBySource(statsBombSource)

        for (s in statsBombCompetitions) {
            val competition = processCompetition(s, competitions, seasons, countries, statsBombSource)
            if (!competitions.contains(competition)) competitions.add(competition)
        }

        return competitions
    }

    fun processCompetition(
            statsBombCompetition: StatsBombCompetition,
            competitions: ArrayList<Competition>,
            seasons: Iterable<Season>,
            countries: Iterable<Country>,
            statsBombSource: Source
    ): Competition {

        var competition = competitions.find { c -> c.compareTo(statsBombCompetition, statsBombSource) }

        if (competition == null) {
            val season = seasons.first { s -> s.name == statsBombCompetition.seasonName }
            val country = countries.first { c -> c.name == statsBombCompetition.countryName }

            competition = Competition(
                    season,
                    country,
                    statsBombCompetition.competitionName,
                    Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase()),
                    statsBombSource,
                    statsBombCompetition.competitionId.toString()
            )

            competitionRepository.save(competition)
        }

        return competition
    }

    fun Competition.compareTo(
            statsBombCompetition: StatsBombCompetition,
            statsBombSource: Source
    ): Boolean =
            this.country.name == statsBombCompetition.countryName
                    && this.season.name == statsBombCompetition.seasonName
                    && this.name == statsBombCompetition.competitionName
                    && this.source.id == statsBombSource.id
}