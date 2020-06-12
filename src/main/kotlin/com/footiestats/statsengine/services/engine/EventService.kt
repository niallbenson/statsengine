package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.EventDTO
import com.footiestats.statsengine.dtos.engine.TeamDTO
import com.footiestats.statsengine.dtos.engine.mappers.EventMapper
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class EventService(
        private val eventRepository: EventRepository,
        private val eventMapper: EventMapper
) {

    fun getPlayerMatchEvents(playerId: Long, matchId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("playerId $playerId is invalid")

        return eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

    fun getMatchEventsByType(matchId: Long, eventTypeId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (eventTypeId < 1) throw EntityIdMustBeGreaterThanZero("eventTypeId $eventTypeId is invalid")

        return eventRepository.findAllByMatch_IdAndType_Id(matchId, eventTypeId)
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

    fun getMatchGoals(matchId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")

        return eventRepository.findAllByMatch_IdAndType_IdAndShot_Outcome_IdOrderByEventIndex(
                matchId, EventTypeEnum.SHOT.id, OutcomeEnum.GOAL.id)
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

}