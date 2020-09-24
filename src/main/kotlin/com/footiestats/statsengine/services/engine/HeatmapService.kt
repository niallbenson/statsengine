package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.HeatmapDTO
import com.footiestats.statsengine.repos.engine.EventRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.math.absoluteValue

private val log = KotlinLogging.logger {}

const val PITCH_WIDTH = 120
const val PITCH_HEIGHT = 80

@Service
class HeatmapService(private val eventRepository: EventRepository) {

    fun getMatchPlayerHeatmap(matchId: Long, playerId: Long, gridSize: Int) {
        val events = eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)

        val rowsCount = PITCH_HEIGHT / gridSize
        val colsCount = PITCH_WIDTH / gridSize

        val rows = Array(rowsCount) { i -> i * gridSize }
        val cols = Array(colsCount) { i -> i * gridSize }

        rows.map { r -> }

        rows.forEach { r ->
            cols.forEach { c ->
                val gridEvents = events.filter { it.location != null && it.location.x >= c.absoluteValue && it.location < r + gridSize }

                log.info { "$r x $c" }

                log.info {  }

            }
        }
    }

    private fun
}
