package com.footiestats.statsengine.services.ingestion.statsbomb

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.engine.Country
import com.footiestats.statsengine.entities.engine.Season
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.entities.statsbomb.mappers.StatsBombCompetitionMapper
import com.footiestats.statsengine.repos.engine.CompetitionRepository
import com.footiestats.statsengine.repos.engine.CountryRepository
import com.footiestats.statsengine.repos.engine.SeasonRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI

@Service
class CompetitionIngestionService(
        private val competitionRepository: CompetitionRepository,
        private val countryRepository: CountryRepository,
        private val seasonRepository: SeasonRepository) {

    fun updateFromStatsBombCompetitions(): Iterable<Competition> {
        val competitions = getStatsBombCompetitions()

        return addOrUpdateCompetitions(competitions)
    }

    private fun getStatsBombCompetitions(): Iterable<StatsBombCompetition> {
        val uri = URI("https://raw.githubusercontent.com/statsbomb/open-data/master/data/competitions.json")
        val jsonResponse = RestTemplate().getForObject<String>(uri)

        return StatsBombCompetitionMapper.fromJson(jsonResponse)
    }

    fun addOrUpdateCompetitions(statsBombCompetitions: Iterable<StatsBombCompetition>): Iterable<Competition> {
        val seasons = processSeasons(statsBombCompetitions)
        val countries = processCountries(statsBombCompetitions)

        return processCompetitions(statsBombCompetitions, countries, seasons)
    }

    fun processSeasons(statsBombCompetitions: Iterable<StatsBombCompetition>): Iterable<Season> {
        var seasons = seasonRepository.findAll()
        val distinctSeasonNames = statsBombCompetitions.map { c -> c.seasonName }.distinct()
        for (s in distinctSeasonNames)
            seasons = processSeason(s, seasons)

        return seasons
    }

    fun processSeason(seasonName: String, seasons: Iterable<Season>): Iterable<Season> {
        val season = seasons.find { s -> s.name == seasonName }
        if (season == null) {
            val newSeason = Season(seasonName)
            seasonRepository.save(newSeason)
            return seasonRepository.findAll()
        }
        return seasons
    }

    fun processCountries(statsBombCompetitions: Iterable<StatsBombCompetition>): Iterable<Country> {
        var countries = countryRepository.findAll()
        val distinctCountryNames = statsBombCompetitions.map { c -> c.countryName }.distinct()
        for (c in distinctCountryNames)
            countries = processCountry(c, countries)

        return countries
    }

    fun processCountry(countryName: String, countries: Iterable<Country>): Iterable<Country> {
        val country = countries.find { c -> c.name == countryName }
        if (country == null) {
            val newCountry = Country(countryName)
            countryRepository.save(newCountry)
            return countryRepository.findAll()
        }
        return countries
    }

    fun processCompetitions(statsBombCompetitions: Iterable<StatsBombCompetition>,
                            countries: Iterable<Country>, seasons: Iterable<Season>): Iterable<Competition> {
        var competitions = competitionRepository.findAll()
        for (c in statsBombCompetitions)
            competitions = processCompetition(c, competitions, seasons, countries)

        return competitions
    }

    fun processCompetition(statsBombCompetition: StatsBombCompetition, competitions: Iterable<Competition>,
                           seasons: Iterable<Season>, countries: Iterable<Country>): Iterable<Competition> {

        val competition = competitions.find { c -> c.compareToStatsBombCompetition(statsBombCompetition) }

        if (competition == null) {
            val season = seasons.first { s -> s.name == statsBombCompetition.seasonName }
            val country = countries.first { c -> c.name == statsBombCompetition.countryName }

            val newCompetition = Competition(
                    season, country, statsBombCompetition.competitionName,
                    Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase()))

            competitionRepository.save(newCompetition)

            return competitionRepository.findAll()
        }

        return competitions
    }
}

private fun Competition.compareToStatsBombCompetition(statsBombCompetition: StatsBombCompetition): Boolean =
        this.country.name.equals(statsBombCompetition.countryName)
                && this.season.name.equals(statsBombCompetition.seasonName)
                && this.name.equals(statsBombCompetition.competitionName)
