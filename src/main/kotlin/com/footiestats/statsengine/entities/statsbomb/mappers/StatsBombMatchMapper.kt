package com.footiestats.statsengine.entities.statsbomb.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.footiestats.statsengine.entities.statsbomb.StatsBombMatch

class StatsBombMatchMapper {
    companion object {
        fun fromJson(json: String): Iterable<StatsBombMatch> {
            val mapper = jacksonObjectMapper()

            return mapper.readValue(json)
        }
    }
}