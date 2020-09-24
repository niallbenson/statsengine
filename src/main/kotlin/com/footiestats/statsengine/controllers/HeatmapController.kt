package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.engine.HeatmapService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/heatmap")
@CrossOrigin("http://localhost:4200", "http://localhost:4201")
class HeatmapController(private val heatmapService: HeatmapService) {

    @GetMapping("/match/{matchId}/player/{playerId}/grid/{gridSize}")
    fun getMatchPlayerHeatmap(@PathVariable matchId: Long,
                              @PathVariable playerId: Long,
                              @PathVariable gridSize: Int) =
        heatmapService.getMatchPlayerHeatmap(matchId, playerId, gridSize)

}
