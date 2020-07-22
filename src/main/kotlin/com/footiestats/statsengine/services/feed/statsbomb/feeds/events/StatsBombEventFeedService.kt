package com.footiestats.statsengine.services.feed.statsbomb.feeds.events

import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEventEntityService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombEventFeedService(
        private val baseEntityService: StatsBombBaseEntityService,
        private val eventEntityService: StatsBombEventEntityService,
        private val statsBombEventMatchAsync: StatsBombEventMatchAsync) {

    fun run() {
        log.info { "Starting StatsBomb Events Feed" }

        val competitionSeasons = baseEntityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            processCompetitionSeason(cs)
        }
    }

    private fun processCompetitionSeason(competitionSeason: CompetitionSeason) {
        log.info {
            "Importing events for competition=${competitionSeason.competition.name} " +
                    "season=${competitionSeason.season.name}"
        }

        val matches = baseEntityService.getMatchesForCompetitionSeason(competitionSeason)

        for (m in matches) {
            if (eventEntityService.getEventsCount(m) == 0L) {
                statsBombEventMatchAsync.processMatch(m)
            }
        }
    }


}
