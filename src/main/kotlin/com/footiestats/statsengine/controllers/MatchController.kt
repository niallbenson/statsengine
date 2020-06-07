package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.MatchService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/match")
@CrossOrigin("http://localhost:4200")
class MatchController(private val matchService: MatchService) {

    @GetMapping("/{id}")
    fun getMatch(@PathVariable id: Long) = matchService.getMatchDto(id)

    @GetMapping("/competition/{competitionId}/season/{seasonId}")
    fun getByCompetitionAndSeasonId(@PathVariable competitionId: Long, @PathVariable seasonId: Long)
            = matchService.getMatchDtos(competitionId, seasonId)

    @GetMapping("/{matchId}/team/{teamId}")
    fun getTeamLineup(@PathVariable matchId: Long, @PathVariable teamId: Long)
            = mat

}