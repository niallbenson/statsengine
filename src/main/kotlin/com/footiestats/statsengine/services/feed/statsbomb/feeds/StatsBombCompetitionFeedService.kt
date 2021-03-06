package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombCompetitionFeedService(
        private val baseEntityService: StatsBombBaseEntityService,
        private val restService: StatsBombRestService) {

    fun run(): ArrayList<CompetitionSeason> {
        log.info { "Updating competitions from StatsBomb" }

        val statsBombCompetitions = restService.getStatsBombCompetitions()

        return processCompetitions(statsBombCompetitions)
    }

    private fun processCompetitions(
            statsBombCompetitions: Iterable<StatsBombCompetition>
    ): ArrayList<CompetitionSeason> {
        log.info { "Processing competitions" }

        val competitionSeasons = ArrayList<CompetitionSeason>()

        for (s in statsBombCompetitions) {
            competitionSeasons.add(getOrCreateCompetitionSeason(s))
        }
        return competitionSeasons
    }

    private fun getOrCreateCompetitionSeason(s: StatsBombCompetition): CompetitionSeason {
        log.info { "Competition: ${s.competitionName} id:${s.competitionId} season: ${s.seasonName}" }

        val competition = baseEntityService.getOrCreateCompetition(s)
        val season = baseEntityService.getOrCreateSeason(s.seasonId.toString(), s.seasonName)

        return baseEntityService.getOrCreateCompetitionSeason(competition, season)
    }
}