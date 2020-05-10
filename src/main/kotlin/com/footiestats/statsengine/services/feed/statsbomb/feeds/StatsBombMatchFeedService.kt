package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.statsbomb.StatsBombMatch
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombUtils
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

    fun updateAllCompetitionSeasons(): ArrayList<Match> {
        val source = entityService.getStatsBombSource()

        val competitionSeasons = entityService.getCompetitionSeasonsByCompetitionSource(source)

        return processStatsBombMatches(competitionSeasons)
    }

    private fun processStatsBombMatches(
            competitionSeasons: Iterable<CompetitionSeason>,
            source: Source
    ): ArrayList<Match> {
        val matchResults = ArrayList<Match>()
        for (cs in competitionSeasons) {
            matchResults.addAll(
                processCompetitionSeason(cs.competition, cs.season, source)
            )
        }
        return matchResults
    }

    private fun processCompetitionSeason(
            competition: Competition,
            season: Season,
            statsBombSource: Source
    ): ArrayList<Match> {
        val statsBombMatches =
                restService.getStatsBombMatches(competition.id.toString(), season.id.toString())

        val competitionSourceIds =
                statsBombMatches.map { m -> m.competition.competitionId.toString() }.distinct()

        val existingMatches =
                entityService.getMatchesForCompetitionExternalIds(competitionSourceIds)

        val matches = ArrayList<Match>()
        for (m in statsBombMatches) {
            matches.add(
                    getOrCreateMatch(m, existingMatches, statsBombSource, competition, season)
            )
        }
        return matches
    }

    private fun getOrCreateMatch(
            statsBombMatch: StatsBombMatch,
            existingMatches: ArrayList<Match>,
            source: Source,
            competition: Competition,
            season: Season
    ): Match {
        var match = existingMatches.find { m -> m.compareTo(statsBombMatch, source) }

        if (match == null) {
            match = Match(
                    matchDate = StatsBombUtils.convertToDate(statsBombMatch.matchDate, statsBombMatch.kickOff),
                    competition = competition,
                    season = season,
                    homeScore = statsBombMatch.homeScore,
                    awayScore = statsBombMatch.awayScore,
                    matchStatus = statsBombMatch.matchStatus,
                    matchWeek = statsBombMatch.matchWeek
            )
        }

        return match
    }

}