package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombDateUtils
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service

@Service
class StatsBombMatchFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): ArrayList<Match> {
        println("Updating matches from StatsBomb")

        val matches = ArrayList<Match>()

        val competitionSeasons = entityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            matches.addAll(
                    processCompetitionSeason(cs.competition, cs.season)
            )
        }
        return matches
    }

    private fun processCompetitionSeason(
            competition: Competition,
            season: Season
    ): ArrayList<Match> {
        val statsBombMatches =
                restService.getStatsBombMatches(competition.sourceExternalId, season.sourceExternalId)

        val matches = ArrayList<Match>()
        for (m in statsBombMatches) {
            matches.add(
                    processMatch(m)
            )
        }
        return matches
    }

    private fun processMatch(statsBombMatch: StatsBombMatch): Match {
        var match = entityService.getMatchByExternalId(statsBombMatch.matchId.toString())

        if (match == null) {
            val competition =
                    entityService.getCompetitionByExternalId(statsBombMatch.competition.competitionId.toString())
                            ?: throw StatsBombEntityNotFound(
                                    "Competition not found for id ${statsBombMatch.competition.competitionId}")

            val season = entityService.getSeasonByExternalId(statsBombMatch.season.seasonId.toString())
                    ?: entityService.getOrCreateSeason(statsBombMatch.season)

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
                    competition,
                    season,
                    homeTeam,
                    awayTeam,
                    ArrayList(homeTeam.managers),
                    ArrayList(awayTeam.managers),
                    statsBombMatch.homeScore,
                    statsBombMatch.awayScore,
                    statsBombMatch.matchStatus,
                    StatsBombDateUtils.convertToDateFromLong(statsBombMatch.lastUpdated)!!,
                    entityService.getOrCreateMatchMetaData(statsBombMatch.metadata),
                    statsBombMatch.matchWeek,
                    entityService.getOrCreateCompetitionStage(statsBombMatch.competitionStage),
                    stadium,
                    referee,
                    entityService.getStatsBombSource(),
                    statsBombMatch.matchId.toString()
            )
            entityService.save(match)
        }
        return match
    }

}