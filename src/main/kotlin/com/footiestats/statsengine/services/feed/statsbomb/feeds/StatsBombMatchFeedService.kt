package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.engine.MatchDTO
import com.footiestats.statsengine.dtos.engine.mappers.MatchMapper
import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombDateUtils
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombMatchFeedService(
        private val baseEntityService: StatsBombBaseEntityService,
        private val restService: StatsBombRestService,
        private val matchMapper: MatchMapper) {

    fun run(): Array<MatchDTO> {
        log.info { "Updating matches from StatsBomb" }

        val matches = ArrayList<Match>()

        val competitionSeasons = baseEntityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            matches.addAll(
                    processCompetitionSeason(cs)
            )
        }
        return matches.map { matchMapper.toDto(it) }.toTypedArray()
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
        var match = baseEntityService.getMatchByExternalId(statsBombMatch.matchId.toString())

        if (match == null) {
            val stadium =
                    if (statsBombMatch.stadium != null)
                        baseEntityService.getOrCreateStadium(statsBombMatch.stadium)
                    else null

            val referee =
                    if (statsBombMatch.referee != null)
                        baseEntityService.getOrCreateReferee(statsBombMatch.referee)
                    else null

            val homeTeam = baseEntityService.getOrCreateTeam(statsBombMatch.homeTeam)

            val awayTeam = baseEntityService.getOrCreateTeam(statsBombMatch.awayTeam)

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
                    baseEntityService.getOrCreateMatchMetaData(statsBombMatch.metadata),
                    baseEntityService.getOrCreateCompetitionStage(statsBombMatch.competitionStage),
                    stadium,
                    referee,
                    baseEntityService.getStatsBombSource()
            )

            baseEntityService.save(match)
        }
        return match
    }

}