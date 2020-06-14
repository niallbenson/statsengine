package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.ShotDTO
import com.footiestats.statsengine.dtos.engine.mappers.exceptions.EventHasNullWhereValueExpected
import com.footiestats.statsengine.entities.engine.events.Event

class ShotMapper(
        private val eventMapper: EventMapper,
        private val playerMapper: PlayerMapper
) {

    fun toDto(event: Event): ShotDTO {
        if (event.shot == null) throw EventHasNullWhereValueExpected("Shot is null")

        val assistPlayer = event.shot?.keyPass?.player

        return ShotDTO(
                eventMapper.toDto(event),
                event.shot!!.shotType.name,
                event.shot!!.outcome.name,
                event.shot!!.bodyPart.name,
                event.shot!!.technique.name,
                if (assistPlayer != null) playerMapper.toDto(assistPlayer) else null
        )
    }
}