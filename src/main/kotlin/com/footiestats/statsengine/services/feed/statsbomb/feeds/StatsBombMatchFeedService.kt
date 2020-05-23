package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombDateUtils
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombMatchFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): ArrayList<Match> {
        log.info { "Updating matches from StatsBomb" }

        val matches = ArrayList<Match>()

        val competitionSeasons = entityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            matches.addAll(
                    processCompetitionSeason(cs)
            )
        }
        return matches
    }

    private fun processCompetitionSeason(
            competitionSeason: CompetitionSeason
    ): ArrayList<Match> {
        val statsBombMatches =
                restService.getStatsBombMatches(
                        competitionSeason.competition.sourceExternalId,
                        competitionSeason.season.sourceExternalId
                )

        val matches = ArrayList<Match>()
        for (m in statsBombMatches) {
            matches.add(
                    processMatch(m, competitionSeason)
            )
        }
        return matches
    }

    private fun processMatch(statsBombMatch: StatsBombMatch, competitionSeason: CompetitionSeason): Match {
        var match = entityService.getMatchByExternalId(statsBombMatch.matchId.toString())

        if (match == null) {
            val stadium =
                    if (statsBombMatch.stadium != null)
                        entityService.getOrCreateStadium(statsBombMatch.stadium!!)
                    else null

            val referee =
                    if (statsBombMatch.referee != null)
                        entityService.getOrCreateReferee(statsBombMatch.referee!!)
                    else null

            val homeTeam = entityService.getOrCreateTeam(statsBombMatch.homeTeam)

            val awayTeam = entityService.getOrCreateTeam(statsBombMatch.awayTeam)

            match = Match(
                    StatsBombDateUtils.convertToDate(statsBombMatch.matchDate, statsBombMatch.kickOff),
                    statsBombMatch.homeScore,
                    statsBombMatch.awayScore,
                    statsBombMatch.matchStatus,
                    StatsBombDateUtils.convertToDateFromLong(statsBombMatch.lastUpdated)!!,
                    statsBombMatch.matchWeek,
                    statsBombMatch.matchId.toString(),
                    competitionSeason,
                    homeTeam,
                    awayTeam,
                    ArrayList(homeTeam.managers),
                    ArrayList(awayTeam.managers),
                    entityService.getOrCreateMatchMetaData(statsBombMatch.metadata),
                    entityService.getOrCreateCompetitionStage(statsBombMatch.competitionStage),
                    stadium,
                    referee,
                    entityService.getStatsBombSource()
            )

            entityService.save(match)
        }
        return match
    }

}