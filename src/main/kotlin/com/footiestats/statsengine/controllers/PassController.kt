package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.PassService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pass")
@CrossOrigin("http://localhost:4200", "http://localhost:4201")
class PassController(private val passService: PassService) {

    @GetMapping("/{passEventId}/overview")
    fun getPassEventOverview(@PathVariable passEventId: Long) = passService.getPassEventOverviewDto(passEventId)

}
