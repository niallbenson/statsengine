package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.ShotService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shot")
@CrossOrigin("http://localhost:4200")
class ShotController(private val shotService: ShotService) {

    @GetMapping("/match/{matchId}/team/{teamId}")
    fun getMatchTeamShots(@PathVariable matchId: Long, @PathVariable teamId: Long)
            = shotService.getMatchTeamShotsDtos(matchId, teamId)
}