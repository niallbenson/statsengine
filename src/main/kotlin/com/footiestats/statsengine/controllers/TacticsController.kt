package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.TacticsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tactics")
@CrossOrigin("http://localhost:4200")
class TacticsController(private val tacticsService: TacticsService) {

    @GetMapping("/match/{matchId}/team/{teamId}")
    fun getMatchTeamTactics(@PathVariable matchId: Long, @PathVariable teamId: Long) =
            tacticsService.getMatchTeamTacticsDto(matchId, teamId)
}