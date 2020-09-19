package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.SeasonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/season")
@CrossOrigin("http://localhost:4200", "http://localhost:4201")
class SeasonController(private val seasonService: SeasonService) {

    @GetMapping("/{id}")
    fun getSeason(@PathVariable id: Long) = seasonService.getSeasonDto(id)
}
