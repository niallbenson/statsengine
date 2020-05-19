package com.footiestats.statsengine.dtos.statsbomb.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.footiestats.statsengine.dtos.statsbomb.StatsBombLineup

class StatsBombLineupsMapper {
    companion object {
        fun fromJson(json: String): Iterable<StatsBombLineup> {
            val mapper = jacksonObjectMapper()

            return mapper.readValue(json)
        }
    }
}