package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.ingestion.statsbomb.CompetitionIngestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/injestion")
class InjestionController(
        private val competitionIngestionService: CompetitionIngestionService
) {
    @GetMapping("/statsbomb/competitions")
    fun updateStatsbombCompetitions() = competitionIngestionService.updateFromStatsBombCompetitions()
}