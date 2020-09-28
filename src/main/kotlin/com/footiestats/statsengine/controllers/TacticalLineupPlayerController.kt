package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.repos.engine.TacticalLineupPlayerRepository
import com.footiestats.statsengine.services.engine.PlayerTacticalLineupService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tactical-lineup-player")
@CrossOrigin("http://localhost:4200", "http://localhost:4201")
class TacticalLineupPlayerController(
        private val tacticalLineupService: PlayerTacticalLineupService
) {

    @GetMapping("/player/{playerId}/match/{matchId}/first")
    fun getFirstInstanceOfPlayerMatchTacticalLineup(@PathVariable playerId : Long, @PathVariable matchId: Long) =
            tacticalLineupService.getFirstInstanceOfPlayerMatchTacticalLineup(playerId, matchId)

}
