package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombLineup
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombLineupTeamNotInMatch
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombLineupsFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): ArrayList<MatchLineup> {
        log.info { "Starting StatsBomb Lineups Feed" }

        val competitionSeasons = entityService.getCompetitionSeasons()

        val lineups = ArrayList<MatchLineup>()

        for (cs in competitionSeasons) {
            lineups.addAll(
                    processCompetitionSeason(cs)
            )
        }
        log.info { "Finished StatsBomb Lineups Feed" }

        return lineups
    }

    private fun processCompetitionSeason(
            competitionSeason: CompetitionSeason
    ): ArrayList<MatchLineup> {
        log.info { "Importing line ups for competition=${competitionSeason.competition.name} " +
                "season=${competitionSeason.season.name}" }

        val matches = entityService.getMatchesForCompetitionSeason(competitionSeason)

        val lineups = ArrayList<MatchLineup>()

        for (m in matches) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            val statsBombLineups = restService.getStatsBombLineups(m.sourceExternalId)

            lineups.addAll(
                    processMatchLineups(m, statsBombLineups)
            )
        }
        return lineups
    }

    private fun processMatchLineups(
            match: Match,
            lineups: Iterable<StatsBombLineup>
    ): Iterable<MatchLineup> {
        val homeStatsBombLineup = lineups.first { it.teamId.toString() == match.homeTeam.sourceExternalId }
        val homeLineup = processTeamLineup(match, homeStatsBombLineup)

        val awayStatsBombLineup = lineups.first { it.teamId.toString() == match.awayTeam.sourceExternalId }
        val awayLineup = processTeamLineup(match, awayStatsBombLineup)

        return arrayListOf(homeLineup, awayLineup)
    }

    private fun processTeamLineup(
            match: Match,
            lineup: StatsBombLineup
    ): MatchLineup {
        if (lineup.teamId.toString() != match.homeTeam.sourceExternalId
                && lineup.teamId.toString() != match.awayTeam.sourceExternalId)
            throw StatsBombLineupTeamNotInMatch("Team ${lineup.teamName} not found in Match id ${match.id}")

        return entityService.getOrCreateMatchLineup(match, lineup)
    }

}
