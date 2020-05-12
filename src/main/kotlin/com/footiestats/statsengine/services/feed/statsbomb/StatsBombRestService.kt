package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.dtos.statsbomb.StatsBombMatch
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombCompetitionMapper
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombMatchMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI

@Service
class StatsBombRestService(private val restTemplate: RestTemplate) {

    fun getStatsBombCompetitions(): Iterable<StatsBombCompetition> {
        val url = "https://raw.githubusercontent.com/statsbomb/open-data/master/data/competitions.json"
        val uri = URI(url)

        println("Retrieving competitions from $url")
        val jsonResponse = restTemplate.getForObject<String>(uri)

        return StatsBombCompetitionMapper.fromJson(jsonResponse)
    }

    fun getStatsBombMatches(
            competitionId: String,
            seasonId: String
    ): Iterable<StatsBombMatch> {

        val url = getCompetitionSeasonUrl(competitionId, seasonId)
        val uri = URI(url)

        println("Retrieving matches from $url")
        val jsonResponse = restTemplate.getForObject<String>(uri)

        return StatsBombMatchMapper.fromJson(jsonResponse)
    }

    private fun getCompetitionSeasonUrl(competitionId: String, seasonId: String) =
            "https://raw.githubusercontent.com/statsbomb/open-data/master/data/matches/$competitionId/$seasonId.json"

}