package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.repos.engine.*
import org.springframework.stereotype.Service

fun Competition.compareTo(
        statsBombCompetition: StatsBombCompetition,
        statsBombSource: Source
): Boolean =
        this.country.name == statsBombCompetition.countryName
                && this.season.name == statsBombCompetition.seasonName
                && this.name == statsBombCompetition.competitionName
                && this.source.id == statsBombSource.id

@Service
class StatsBombCompetitionFeedService(
        private val competitionRepository: CompetitionRepository,
        private val countryRepository: CountryRepository,
        private val seasonRepository: SeasonRepository,
        private val statsBombEntityService: StatsBombEntityService,
        private val statsBombRestService: StatsBombRestService) {

    fun updateFromStatsBombCompetitions(): ArrayList<Competition> {
        println("Updating competitions from StatsBomb")

        val statsBombCompetitions = statsBombRestService.getStatsBombCompetitions()

        val statsBombSource = statsBombEntityService.getStatsBombSource()

        return addNewAndReturnAll(statsBombCompetitions, statsBombSource)
    }

    private fun addNewAndReturnAll(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            statsBombSource: Source
    ): ArrayList<Competition> {

        val seasons = processSeasons(statsBombCompetitions, statsBombSource)
        val countries = processCountries(statsBombCompetitions)

        return processCompetitions(statsBombCompetitions, countries, seasons, statsBombSource)
    }

    private fun processSeasons(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            statsBombSource: Source
    ): ArrayList<Season> {
        println("Processing seasons")

        var seasons = seasonRepository.findBySource(statsBombSource)
        val seasonIdToNameMap = statsBombCompetitions.map { s -> s.seasonId to s.seasonName }.toMap()

        for (s in seasonIdToNameMap)
            seasons = processSeason(s.value, s.key.toString(), seasons, statsBombSource)

        return seasons
    }

    private fun processSeason(
            seasonName: String,
            seasonId: String,
            seasons: ArrayList<Season>,
            statsBombSource: Source
    ): ArrayList<Season> {
        println("Season: $seasonName id:$seasonId")

        val season = seasons.find { s -> s.name == seasonName }
        if (season == null) {
            println("Added")

            val newSeason = Season(seasonName, statsBombSource, seasonId)
            seasonRepository.save(newSeason)

            seasons.add(newSeason)
        }
        return seasons
    }

    private fun processCountries(statsBombCompetitions: Iterable<StatsBombCompetition>): ArrayList<Country> {
        println("Processing countries")

        var countries = countryRepository.findAll()
        val distinctCountryNames = statsBombCompetitions.map { c -> c.countryName }.distinct()

        for (c in distinctCountryNames)
            countries = processCountry(c, countries)

        return countries
    }

    private fun processCountry(
            countryName: String,
            countries: ArrayList<Country>
    ): ArrayList<Country> {
        println("Country: $countryName")

        val country = countries.find { c -> c.name == countryName }
        if (country == null) {
            val newCountry = Country(countryName)
            countryRepository.save(newCountry)

            countries.add(newCountry)
        }
        return countries
    }

    private fun processCompetitions(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            countries: Iterable<Country>,
            seasons: Iterable<Season>,
            statsBombSource: Source
    ): ArrayList<Competition> {
        println("Processing competitions")

        val competitions = competitionRepository.findAllBySource(statsBombSource)

        for (s in statsBombCompetitions) {
            val competition = processCompetition(s, competitions, seasons, countries, statsBombSource)
            if (!competitions.contains(competition)) competitions.add(competition)
        }

        return competitions
    }

    private fun processCompetition(
            statsBombCompetition: StatsBombCompetition,
            competitions: ArrayList<Competition>,
            seasons: Iterable<Season>,
            countries: Iterable<Country>,
            statsBombSource: Source
    ): Competition {
        println("Competition: ${statsBombCompetition.competitionName} id:${statsBombCompetition.competitionId}")

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

}