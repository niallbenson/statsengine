package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.exceptions.EventHasUnexpectedNullValue
import com.footiestats.statsengine.dtos.engine.mappers.exceptions.UnexpectedEventType
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.TacticalLineupPlayer
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import org.springframework.stereotype.Service

@Service
class PassEventOverviewItemMapper(
        private val tacticalLineupPlayerMapper: TacticalLineupPlayerMapper,
        private val location2DMapper: Location2DMapper
) {

    fun toDtoFromPassEvent(passEvent: Event, lineupPlayer: TacticalLineupPlayer): PassEventOverviewItemDTO {
        if (passEvent.type.id != EventTypeEnum.PASS.id)
            throw UnexpectedEventType("Expected Pass, got ${passEvent.type.name}")

        val startLocation2D = passEvent.location
                ?: throw EventHasUnexpectedNullValue("Pass event does not have location")

        val pass = passEvent.pass
                ?: throw EventHasUnexpectedNullValue("Pass event does not have pass entity")

        return PassEventOverviewItemDTO(
                passEvent.id ?: -1,
                passEvent.eventTeam.id ?: -1,
                tacticalLineupPlayerMapper.toDto(lineupPlayer),
                passEvent.type.name,
                location2DMapper.toDto(startLocation2D),
                location2DMapper.toDto(pass.endLocation),
                false, ""
        )
    }

    fun toDtoFromAnyEventType(event: Event, lineupPlayer: TacticalLineupPlayer): PassEventOverviewItemDTO {
        val startLocation2D = event.location
                ?: throw EventHasUnexpectedNullValue("${event.type.name} event does not have location")

        return PassEventOverviewItemDTO(
                event.id ?: -1,
                event.eventTeam.id ?: -1,
                tacticalLineupPlayerMapper.toDto(lineupPlayer),
                event.type.name,
                location2DMapper.toDto(startLocation2D),
                null,
                false, ""
        )
    }

}
