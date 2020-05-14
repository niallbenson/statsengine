package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombManager
import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.dtos.statsbomb.StatsBombTeam
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombUtils
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service

fun Match.compareTo(
        statsBombMatch: StatsBombMatch,
        statsBombSource: Source
): Boolean =
        this.competition.source.id == statsBombSource.id
                && this.competition.sourceExternalId == statsBombMatch.competition.competitionId.toString()
                && this.homeTeam.name == statsBombMatch.homeTeam.teamName
                && this.awayTeam.name == statsBombMatch.awayTeam.teamName
                && this.matchDate == StatsBombUtils.convertToDate(statsBombMatch.matchDate, statsBombMatch.kickOff)

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
                restService.getStatsBombMatches(competition.id.toString(), season.id.toString())

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



            match = Match(

            )
        }

        return match
    }

    private fun getOrCreateTeam(statsBombTeam: StatsBombTeam) {
        var team = entityService.getTeamByExternalId(statsBombTeam.teamId.toString())

        if (team == null) {
            team = Team(

            )
        }
    }

    private fun processManagers(statsBombManagers: Iterable<StatsBombManager>) {
        for (m in statsBombManagers) {
            val country = entityService.getOrCreateCountry(m.country)


        }
    }

}