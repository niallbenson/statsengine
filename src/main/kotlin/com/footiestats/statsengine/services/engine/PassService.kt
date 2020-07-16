package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.Location2DDTO
import com.footiestats.statsengine.dtos.engine.PlayerDTO
import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewItemDTO
import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewDTO
import com.footiestats.statsengine.dtos.engine.mappers.PassEventOverviewItemMapper
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.repository.findByIdOrNull

class PassService(
        private val eventRepository: EventRepository,
        private val passEventOverviewItemMapper: PassEventOverviewItemMapper
) {

    fun getPassEventOverviewDto(eventId: Long): PassEventOverviewDTO {
        if (eventId < 1) throw EntityIdMustBeGreaterThanZero("eventId $eventId is invalid")

        val passEvent = eventRepository.findByIdOrNull(eventId)
                ?: throw EntityNotFound("Pass event not found for $eventId")

        val previousEvent = passEvent.relatedEvents.first { it.eventIndex < passEvent.eventIndex }

        return PassEventOverviewDTO(
                eventId,
                passEventOverviewItemMapper.toDto(previousEvent),
                PassEventOverviewItemDTO(2, PlayerDTO(0, "", "", ""),
                        "", Location2DDTO(0.0,0.0), Location2DDTO(0.0,0.0), false, ""),
                PassEventOverviewItemDTO(3, PlayerDTO(0, "", "", ""),
                        "", Location2DDTO(0.0,0.0), Location2DDTO(0.0,0.0), false, ""),
                PassEventOverviewItemDTO(4, PlayerDTO(0, "", "", ""),
                        "", Location2DDTO(0.0,0.0), Location2DDTO(0.0,0.0), false, "")
        )
    }
}
