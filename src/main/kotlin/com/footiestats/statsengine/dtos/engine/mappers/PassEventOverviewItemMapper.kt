package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.Location2DDTO
import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.exceptions.UnexpectedEventType
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import org.springframework.stereotype.Service

@Service
class PassEventOverviewItemMapper(private val playerMapper: PlayerMapper) {

    fun toDto(passEvent: Event): PassEventOverviewItemDTO {
        if (passEvent.type.id != EventTypeEnum.PASS.id)
            throw UnexpectedEventType("Expected Pass, got ${passEvent.type.name}")

        val playerDto = if (passEvent.player != null) {
            playerMapper.toDto(passEvent.player!!)
        } else null

        return PassEventOverviewItemDTO(
                passEvent.id ?: -1,
                playerDto,
                "", Location2DDTO(0.0, 0.0),
                Location2DDTO(0.0, 0.0), false, ""
        )
    }
}
