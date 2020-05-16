package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import org.springframework.stereotype.Service

@Service
class StatsBombCompetitionFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): ArrayList<CompetitionSeason> {
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
            competitionSeasons.add(getOrCreateCompetitionSeason(s))
        }
        return competitionSeasons
    }

    private fun getOrCreateCompetitionSeason(s: StatsBombCompetition): CompetitionSeason {
        println("Competition: ${s.competitionName} id:${s.competitionId} season: ${s.seasonName}")

        val competition = entityService.getOrCreateCompetition(s)
        val season = entityService.getOrCreateSeason(s.seasonId.toString(), s.seasonName)

        return entityService.getOrCreateCompetitionSeason(competition, season)
    }
}