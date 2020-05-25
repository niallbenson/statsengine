package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.TeamService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/team")
class TeamController(private val teamService: TeamService) {

    @GetMapping("/{id}")
    fun getTeam(@PathVariable id: Long) = teamService.getTeam(id)

}