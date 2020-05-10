package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import org.springframework.stereotype.Service

@Service
class StatsBombCompetitionFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun updateFromStatsBombCompetitions(): ArrayList<Competition> {
        println("Updating competitions from StatsBomb")

        val statsBombCompetitions = restService.getStatsBombCompetitions()

        val statsBombSource = entityService.getStatsBombSource()

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

        var seasons = entityService.getSeasonsBySource(statsBombSource)
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
            entityService.save(newSeason)

            seasons.add(newSeason)
        }
        return seasons
    }

    private fun processCountries(statsBombCompetitions: Iterable<StatsBombCompetition>): ArrayList<Country> {
        println("Processing countries")

        var countries = entityService.getAllCountries()
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
            entityService.save(newCountry)

            countries.add(newCountry)
        }
        return countries
    }

    private fun processCompetitions(
            statsBombCompetitions: Iterable<StatsBombCompetition>,
            countries: ArrayList<Country>,
            seasons: ArrayList<Season>,
            statsBombSource: Source
    ): ArrayList<Competition> {
        println("Processing competitions")

        val competitions = entityService.getCompetitionsBySouce(statsBombSource)
        val competitionSeasons = entityService.getCompetitionSeasonsForCompetitions(competitions)

        for (s in statsBombCompetitions) {
            println("Competition: ${s.competitionName} id:${s.competitionId}")

            val competition = getOrCreateCompetition(s, statsBombSource, competitions, countries)
            if (!competitions.contains(competition)) competitions.add(competition)

            val competitionSeason =
                    getOrCreateCompetitionSeason(s, statsBombSource, competition, competitionSeasons, seasons)
            if (!competitionSeasons.contains(competitionSeason)) competitionSeasons.add(competitionSeason)
        }
        return competitions
    }

    private fun getOrCreateCompetition(
            statsBombCompetition: StatsBombCompetition,
            statsBombSource: Source,
            competitions: ArrayList<Competition>,
            countries: ArrayList<Country>
    ): Competition {
        var competition = competitions.find { c ->
            c.source.id == statsBombSource.id && c.sourceExternalId == statsBombCompetition.competitionId.toString()
        }

        if (competition == null) {
            val country = countries.first { c -> c.name == statsBombCompetition.countryName }

            competition = Competition(
                    country,
                    statsBombCompetition.competitionName,
                    Gender.valueOf(statsBombCompetition.competitionGender.toUpperCase()),
                    statsBombSource,
                    statsBombCompetition.competitionId.toString())

            entityService.save(competition)
        }
        return competition
    }

    private fun getOrCreateCompetitionSeason(
            statsBombCompetition: StatsBombCompetition,
            statsBombSource: Source,
            competition: Competition,
            competitionSeasons: ArrayList<CompetitionSeason>,
            seasons: ArrayList<Season>
    ): CompetitionSeason {
        val season = seasons.first { s ->
            s.source.id == statsBombSource.id && s.sourceExternalId == statsBombCompetition.seasonId.toString()
        }

        var competitionSeason = competitionSeasons.find { cs ->
            cs.competition.id == competition.id && cs.season.id == season.id
        }

        if (competitionSeason == null) {
            competitionSeason = CompetitionSeason(competition, season)
            entityService.save(competitionSeason)
        }
        return competitionSeason
    }
}