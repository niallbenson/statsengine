package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import org.springframework.stereotype.Service

@Service
class StatsBombCompetitionFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun updateFromStatsBombCompetitions(): ArrayList<CompetitionSeason> {
        println("Updating competitions from StatsBomb")

        val statsBombCompetitions = restService.getStatsBombCompetitions()

        return processCompetitions(statsBombCompetitions)
    }

    private fun processCompetitions(
            statsBombCompetitions: Iterable<StatsBombCompetition>
    ): ArrayList<CompetitionSeason> {
        println("Processing competitions")

        val competitionSeasons = ArrayList<CompetitionSeason>()

        for (s in statsBombCompetitions) {
            println("Competition: ${s.competitionName} id:${s.competitionId} season: ${s.seasonName}")

            val competition = entityService.getOrCreateCompetition(s)
            val season = entityService.getOrCreateSeason(s.seasonName)
            val competitionSeason =
                    entityService.getOrCreateCompetitionSeason(competition, season)

            competitionSeasons.add(competitionSeason)
        }
        return competitionSeasons
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