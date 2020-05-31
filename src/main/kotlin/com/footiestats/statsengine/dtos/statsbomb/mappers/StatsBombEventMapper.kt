package com.footiestats.statsengine.dtos.statsbomb.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.footiestats.statsengine.dtos.statsbomb.StatsBombEvent
import com.footiestats.statsengine.entities.engine.events.Event

class StatsBombEventMapper {
    companion object {
        fun fromJson(json: String): Iterable<StatsBombEvent> {
            val mapper = jacksonObjectMapper()

            return mapper.readValue(json)
        }
    }
}