package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository: EventRepository) {

    fun getPlayerMatchEvents(playerId: Long, matchId: Long): ArrayList<Event> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("playerId $playerId is invalid")

        return eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
    }
}