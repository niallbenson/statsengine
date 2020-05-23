package com.footiestats.statsengine.dtos.statsbomb.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.engine.enums.Gender

class StatsBombCompetitionMapper {
    companion object {
        fun fromJson(json: String): Iterable<StatsBombCompetition> {
            val mapper = jacksonObjectMapper()

            return mapper.readValue(json)
        }
    }
}