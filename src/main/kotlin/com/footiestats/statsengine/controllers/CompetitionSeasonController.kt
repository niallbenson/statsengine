package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.CompetitionSeasonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/competitionSeason")
class CompetitionSeasonController(private val competitionSeasonService: CompetitionSeasonService) {

    @GetMapping("")
    fun getAll() = competitionSeasonService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = competitionSeasonService.getOne(id)

}