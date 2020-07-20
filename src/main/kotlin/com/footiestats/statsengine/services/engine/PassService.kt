package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewDTO
import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.PassEventOverviewItemMapper
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.TacticalLineupPlayer
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import com.footiestats.statsengine.services.engine.exceptions.ExpectedRelatedEventNotFound
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PassService(
        private val eventRepository: EventRepository,
        private val passEventOverviewItemMapper: PassEventOverviewItemMapper,
        private val passAnalysisService: PassAnalysisService
) {

    fun getPassEventOverviewDto(eventId: Long): PassEventOverviewDTO {
        if (eventId < 1) throw EntityIdMustBeGreaterThanZero("eventId $eventId is invalid")

        val passEvent = eventRepository.findByIdOrNull(eventId)
                ?: throw EntityNotFound("Pass event not found for $eventId")

        val passEventDto = getOverviewItemDTOForPassEvent(passEvent)

        val previousEventDto = getPreviousEventDto(passEvent)

        val target = passEvent.relatedEvents.find { it.type.id == EventTypeEnum.BALL_RECEIPT.id }
        val targetDto = getTargetDto(target)

        val nextEvenDto = getNextEventDto(passEvent, target)

        return PassEventOverviewDTO(
                eventId,
                passEvent.match.id ?: -1,
                passEvent.period,
                passEvent.minute,
                passEvent.second,
                previousEventDto,
                passEventDto,
                targetDto,
                nextEvenDto
        )
    }

    private fun getPreviousEventDto(passEvent: Event): PassEventOverviewItemDTO {
        val event = eventRepository.findByMatch_IdAndEventIndex(
                passEvent.match.id!!, passEvent.eventIndex - 1)

        return getOverviewItemDTOForAnyEventType(event)
    }

    private fun getLineupPlayer(event: Event): TacticalLineupPlayer {
        val lineupPlayerResults = eventRepository.getTacticalLineupPlayerAtEventIndex(
                event.match.id ?: -1, event.player?.id ?: -1, event.eventIndex,
                EventTypeEnum.STARTING_XI.id, EventTypeEnum.TACTICAL_SHIFT.id,
                PageRequest.of(0, 1))

        return if (lineupPlayerResults.isNotEmpty()) {
            lineupPlayerResults[0]
        } else {
            TacticalLineupPlayer(0, event.player!!, getUnknownPosition())
        }
    }

    private fun getUnknownPosition() = Position("Unknown", Source("N/A"), "N/A")

    private fun getNextEventDto(passEvent: Event, target: Event?): PassEventOverviewItemDTO {
        val event = if (passAnalysisService.isPassAccurate(passEvent) && target != null) {
            target
        } else getNextEventForInaccuratePass(passEvent)

        return getOverviewItemDTOForAnyEventType(event)
    }

    private fun getTargetDto(target: Event?): PassEventOverviewItemDTO? {
        return if (target != null) {
            getOverviewItemDTOForAnyEventType(target)
        } else null
    }

    private fun getNextEventForInaccuratePass(passEvent: Event) =
            passEvent.relatedEvents.find {
                it.type.id != EventTypeEnum.BALL_RECEIPT.id && it.eventIndex > passEvent.eventIndex
            } ?: throw ExpectedRelatedEventNotFound("Inaccurate Pass has no next event")

    private fun getOverviewItemDTOForPassEvent(event: Event): PassEventOverviewItemDTO {
        val lineupPlayer = getLineupPlayer(event)

        return passEventOverviewItemMapper.toDtoFromPassEvent(event, lineupPlayer)
    }

    private fun getOverviewItemDTOForAnyEventType(event: Event): PassEventOverviewItemDTO {
        val lineupPlayer = getLineupPlayer(event)

        return passEventOverviewItemMapper.toDtoFromAnyEventType(event, lineupPlayer)
    }
}
