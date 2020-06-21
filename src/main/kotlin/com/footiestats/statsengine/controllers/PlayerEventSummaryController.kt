package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.AttackingEventSummaryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/player-event-summary")
@CrossOrigin("http://localhost:4200")
class PlayerEventSummaryController(
        private val attackingEventSummaryService: AttackingEventSummaryService
) {

    @GetMapping("/player/{playerId}/match/{matchId}/attacking")
    fun getPlayerMatchAttackingEventSummary(@PathVariable playerId: Long, @PathVariable matchId: Long) =
            attackingEventSummaryService.getPlayerMatchAttackSummaryDto(playerId, matchId)
}