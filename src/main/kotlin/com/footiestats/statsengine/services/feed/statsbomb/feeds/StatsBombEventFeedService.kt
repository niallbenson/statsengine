package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombEventFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): Boolean {
        log.info { "Starting StatsBomb Events Feed" }

        val competitionSeasons = entityService.getCompetitionSeasons()

        for (cs in competitionSeasons) processCompetitionSeason(cs)

        return true
    }

    private fun processCompetitionSeason(competitionSeason: CompetitionSeason) {
        log.info { "Importing events for competition=${competitionSeason.competition.name} " +
                "season=${competitionSeason.season.name}" }

        val matches = entityService.getMatchesForCompetitionSeason(competitionSeason)

        for (m in matches) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            val statsBombEvents = restService.getStatsBombEvents(m.sourceExternalId)
        }

    }


}