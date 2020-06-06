package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.CompetitionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/competition")
@CrossOrigin("http://localhost:4200")
class CompetitionController(private val competitionService: CompetitionService) {

    @GetMapping("")
    fun getCompetitions() = competitionService.getCompetitionDtos()

    @GetMapping("/{id}")
    fun getCompetition(@PathVariable id: Long) = competitionService.getCompetition(id)
}