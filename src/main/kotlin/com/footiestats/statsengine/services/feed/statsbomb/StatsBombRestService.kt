package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.dtos.statsbomb.StatsBombEvent
import com.footiestats.statsengine.dtos.statsbomb.StatsBombLineup
import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombCompetitionMapper
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombEventMapper
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombLineupsMapper
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombMatchMapper
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombUrlResourceNotAvailable
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI

private val log = KotlinLogging.logger {}

@Service
class StatsBombRestService(private val restTemplate: RestTemplate) {

    fun getStatsBombCompetitions(): Iterable<StatsBombCompetition> {
        val url = "https://raw.githubusercontent.com/statsbomb/open-data/master/data/competitions.json"
        val uri = URI(url)

        log.info { "Retrieving competitions from $url" }
        val jsonResponse = getJsonResponseWithTries(uri, 3)

        return StatsBombCompetitionMapper.fromJson(jsonResponse)
    }

    fun getStatsBombMatches(
            competitionId: String,
            seasonId: String
    ): Iterable<StatsBombMatch> {

        val url = getCompetitionSeasonUrl(competitionId, seasonId)
        val uri = URI(url)

        log.info { "Retrieving matches from $url" }
        val jsonResponse = getJsonResponseWithTries(uri, 3)

        return StatsBombMatchMapper.fromJson(jsonResponse)
    }

    private fun getCompetitionSeasonUrl(competitionId: String, seasonId: String) =
            "https://raw.githubusercontent.com/statsbomb/open-data/master/data/matches/$competitionId/$seasonId.json"

    fun getStatsBombLineups(matchId: String): Iterable<StatsBombLineup> {
        val url = getMatchLineupsUrl(matchId)
        val uri = URI(url)

        log.info { "Retrieving lineups from $url" }
        val jsonResponse = getJsonResponseWithTries(uri, 3)

        return StatsBombLineupsMapper.fromJson(jsonResponse)
    }

    private fun getMatchLineupsUrl(matchId: String) =
            "https://raw.githubusercontent.com/statsbomb/open-data/master/data/lineups/$matchId.json"

    fun getStatsBombEvents(matchId: String): Iterable<StatsBombEvent> {
        val url = getMatchEventsUrl(matchId)
        val uri = URI(url)

        log.info { "Retrieving events from $url" }
        val jsonResponse = getJsonResponseWithTries(uri, 3)

        return StatsBombEventMapper.fromJson(jsonResponse)
    }

    private fun getMatchEventsUrl(matchId: String) =
            "https://raw.githubusercontent.com/statsbomb/open-data/master/data/events/$matchId.json"

    private fun getJsonResponseWithTries(uri: URI, tries: Int): String {
        var attempt = 1
        var successResponse: String? = null

        while (attempt <= tries && successResponse == null) {
            try {
                val jsonResponse = restTemplate.getForObject<String>(uri)

                successResponse = jsonResponse
            } catch (e: Exception) {
                log.error { "Attempt $attempt or $tries failed with: ${e.message}" }
                attempt++
            }
        }

        return successResponse
                ?: throw StatsBombUrlResourceNotAvailable("Failed $attempt time(s) for ${uri.toURL()}")
    }

}