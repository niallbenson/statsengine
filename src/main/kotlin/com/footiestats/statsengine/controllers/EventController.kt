package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.EventService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/event")
@CrossOrigin("http://localhost:4200", "http://localhost:4201")
class EventController(private val eventService: EventService) {

    @GetMapping("/player/{playerId}/match/{matchId}")
    fun getPlayerMatchEvents(@PathVariable playerId: Long, @PathVariable matchId: Long) =
            eventService.getPlayerMatchEvents(playerId, matchId)

    @GetMapping("/match/{matchId}/type/{eventTypeId}")
    fun getMatchEventsByType(@PathVariable matchId: Long, @PathVariable eventTypeId: Long) =
            eventService.getMatchEventsByType(matchId, eventTypeId)

    @GetMapping("/match/{matchId}/team/{teamId}/goals")
    fun getMatchTeamGoals(@PathVariable matchId: Long, @PathVariable teamId: Long) =
            eventService.getMatchTeamGoals(matchId, teamId)

    @GetMapping("/match/{matchId}/all")
    fun getMatchEventsAll(@PathVariable matchId: Long) =
            eventService.getMatchEventDetailListItemDtos(matchId)

    @GetMapping("/match/{matchId}/all/player/{playerId}")
    fun getPlayerMatchEventsAll(@PathVariable matchId: Long, @PathVariable playerId: Long) =
            eventService.getPlayerMatchEventDetailListItemDtos(matchId, playerId)

}
