package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.PlayerMatchTimelineService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/player-match-timeline")
@CrossOrigin("http://localhost:4200")
class PlayerMatchTimelineController(
        private val playerMatchTimelineService: PlayerMatchTimelineService
) {

    @GetMapping("/player/{playerId}/match/{matchId}")
    fun getPlayerMatchAttackingEventSummary(@PathVariable playerId: Long, @PathVariable matchId: Long) =
            playerMatchTimelineService.getPlayerMatchTimelineDto(playerId, matchId)

    @GetMapping("/player/{playerId}/match/{matchId}/key")
    fun getPlayerKeyAttackingEventsSummary(@PathVariable playerId: Long, @PathVariable matchId: Long) =
            playerMatchTimelineService.getPlayerMatchTimelineKeyEventsDto(playerId, matchId)

}