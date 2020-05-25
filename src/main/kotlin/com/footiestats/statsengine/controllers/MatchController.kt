package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.MatchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/match")
class MatchController(private val matchService: MatchService) {

    @GetMapping("/{id}")
    fun getMatch(@PathVariable id: Long) = matchService.getMatch(id)

//    @GetMapping("/competitionSeason/{id}")
//    fun getByCompetitionSeasonId(@PathVariable id: Long) = matchService.getByCompetitionSeasonId(id)
}