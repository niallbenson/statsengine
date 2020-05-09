package com.footiestats.statsengine.entities.statsbomb.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition

class StatsBombCompetitionMapper {
    companion object {
        fun fromJson(json: String): Iterable<StatsBombCompetition> {
            val mapper = jacksonObjectMapper()

            return mapper.readValue(json)
        }
    }
}