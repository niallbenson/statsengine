package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.EventDTO
import com.footiestats.statsengine.entities.engine.events.Event
import org.springframework.stereotype.Service

@Service
class EventMapper(
        private val playerMapper: PlayerMapper,
        private val teamMapper: TeamMapper
) {

    fun toDto(event: Event): EventDTO {
        val player =
                if (event.player != null)
                    playerMapper.toDto(event.player!!)
                else null

        return EventDTO(
                event.id ?: -1,
                player,
                event.type.name,
                event.period,
                event.minute,
                event.second,
                teamMapper.toDto(event.eventTeam),
                teamMapper.toDto(event.possessionTeam)
        )
    }
}