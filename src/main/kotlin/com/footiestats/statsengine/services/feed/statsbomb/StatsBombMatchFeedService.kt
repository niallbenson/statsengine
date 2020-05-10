package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.statsbomb.StatsBombMatch
import com.footiestats.statsengine.repos.engine.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

fun Match.compareTo(
        statsBombMatch: StatsBombMatch,
        statsBombSource: Source
): Boolean =
        this.competition.sourceExternalId == statsBombMatch.competition.competitionId.toString()
                && this.homeTeam.name == statsBombMatch.homeTeam.teamName
                && this.awayTeam.name == statsBombMatch.awayTeam.teamName
                && this.matchDate == StatsBombUtils.convertToDate(statsBombMatch.matchDate, statsBombMatch.kickOff)

@Service
class StatsBombMatchFeedService(
        private val matchRepository: MatchRepository,
        private val competitionRepository: CompetitionRepository,
        private val teamRepository: TeamRepository,
        private val countryRepository: CountryRepository,
        private val managerRepository: ManagerRepository,
        private val metadataRepository: MetadataRepository,
        private val competitionStageRepository: CompetitionStageRepository,
        private val stadiumRepository: StadiumRepository,
        private val refereeRepository: RefereeRepository,
        private val statsBombEntityService: StatsBombEntityService,
        private val statsBombRestService: StatsBombRestService) {

    fun updateAllCompetitionSeasons(): ArrayList<Match> {
        val statsBombSource = statsBombEntityService.getStatsBombSource()

        val competitions = competitionRepository.findAllBySource(statsBombSource)

        val matchResults = HashSet<StatsBombMatch>()
        for (c in competitions) {
            try {
                var r = statsBombRestService.getStasBombMatches(c.sourceExternalId, c.season.sourceExternalId)

                matchResults.addAll(r)
            } catch (ex: HttpClientErrorException) {
                println("Unable to open ${c.sourceExternalId} ${c.season.sourceExternalId}")
            }
        }

        return ArrayList()
    }

    fun processMatches(statsBombMatches: Iterable<StatsBombMatch>, statsBombSource: Source) {
        val existingMatches = matchRepository.findAll()

        for (m in statsBombMatches) {
            processMatch(m, existingMatches, statsBombSource)
        }
    }

    fun processMatch(statsBombMatch: StatsBombMatch, existingMatches: ArrayList<Match>, statsBombSource: Source) {
        val existingMatch = existingMatches.find { m -> m.compareTo(statsBombMatch, statsBombSource) }

        if (existingMatch == null) {

        }
    }

}