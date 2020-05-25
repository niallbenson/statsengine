package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.SeasonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/season")
class SeasonController(private val seasonService: SeasonService) {

    @GetMapping("/{id}")
    fun getSeason(@PathVariable id: Long) = seasonService.getSeason(id)
}