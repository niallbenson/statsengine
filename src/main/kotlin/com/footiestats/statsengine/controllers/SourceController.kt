package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.SourceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/source")
class SourceController(private val sourceService: SourceService) {

    @GetMapping("/{id}")
    fun getSource(@PathVariable id: Long) = sourceService.getSource(id)
}