package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.CompetitionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/competition")
class CompetitionController(private val competitionService: CompetitionService) {

    @GetMapping("/{id}")
    fun getCompetition(@PathVariable id: Long) = competitionService.getCompetition(id)
}