package com.footiestats.statsengine.services.feed.statsbomb.feeds;

import com.footiestats.statsengine.dtos.statsbomb.StatsBombLineup
import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Season
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import mu.KotlinLogging
import org.springframework.stereotype.Service;

private val log = KotlinLogging.logger {}

@Service
class StatsBombLineupsFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run() {
        log.info { "Starting StatsBomb Lineups Feed" }

        val competitionSeasons = entityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            log.info { "About to run feed for competition=${cs.competition.name} season=${cs.season.name}" }

            processCompetitionSeason(
                    cs.competition,
                    cs.season
            )
        }

        log.info{ "Finished StatsBomb Lineups Feed" }
    }

    private fun processCompetitionSeason(
            competition: Competition,
            season: Season
    ): ArrayList<StatsBombLineup> {
        val matches = entityService.getMatchesForCompetitionAndSeason(competition, season)

        for (m in matches) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            val statsBombLineups = restService.getStatsBombLineups(m.sourceExternalId)
        }

        return ArrayList()
    }

    private fun processMatchLineups(match: Match, linesups: StatsBombLineup) {

    }

}
