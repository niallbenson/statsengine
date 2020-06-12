package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.EventService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/event")
@CrossOrigin("http://localhost:4200")
class EventController(private val eventService: EventService) {

    @GetMapping("/player/{playerId}/match/{matchId}")
    fun getPlayerMatchEvents(@PathVariable playerId: Long, @PathVariable matchId: Long) =
            eventService.getPlayerMatchEvents(playerId, matchId)

    @GetMapping("/match/{matchId}/type/{eventTypeId}")
    fun getMatchEventsByType(@PathVariable matchId: Long, @PathVariable eventTypeId: Long) =
            eventService.getMatchEventsByType(matchId, eventTypeId)

    @GetMapping("/match/{matchId}/goals")
    fun getMatchGoals(@PathVariable matchId: Long) = eventService.getMatchGoals(matchId)

}