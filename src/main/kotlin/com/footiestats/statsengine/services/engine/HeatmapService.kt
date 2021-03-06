package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.HeatmapGridCellDTO
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.InvalidPitchGridSize
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

const val PITCH_WIDTH = 120
const val PITCH_HEIGHT = 80

@Service
class HeatmapService(private val eventRepository: EventRepository) {

    fun getMatchPlayerHeatmap(matchId: Long, playerId: Long, gridSize: Int): List<HeatmapGridCellDTO> {
        validateGridSize(gridSize)

        val events = eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)

        log.info { "Generating Match Player Heatmap for $matchId $playerId with grid size $gridSize with ${events.size} events" }

        val isAwayTeam = isAwayTeam(events)

        val rowsCount = PITCH_HEIGHT / gridSize
        val colsCount = PITCH_WIDTH / gridSize

        val rows = Array(rowsCount) { i -> i * gridSize }
        val cols = Array(colsCount) { i -> i * gridSize }

        val cells = ArrayList<HeatmapGridCellDTO>()

        for (y in rows.indices) {
            for (x in cols.indices) {
                val yValue = if (isAwayTeam) rows[rows.size - 1 - y] else rows[y]
                val xValue = if (isAwayTeam) cols[cols.size - 1 - x] else cols[x]

                val gridEvents = events.filter { e -> isEventInGridCell(e, xValue, yValue, gridSize) }

                if (gridEvents.isNotEmpty()) {
                    val eventIds = gridEvents.map { it.id ?: -1 }.toTypedArray()

                    cells.add(
                            HeatmapGridCellDTO(cols[x], rows[y], gridEvents.count(), eventIds)
                    )
                }
            }
        }

        val unmatchedEvents = events.filter { e ->
            cells.none { c -> c.eventIds.contains( e.id ) }
        }.map { e -> e.id }.toTypedArray()

        log.info { "${unmatchedEvents.size} events not in Heatmap: ${unmatchedEvents.joinToString { it.toString() }}" }

        return cells
    }

    private fun isAwayTeam(events: Set<Event>): Boolean {
        val event = events.iterator().next()
        val playerTeam = event.eventTeam
        val match = event.match

        return playerTeam.id == match.awayTeam.id
    }

    private fun validateGridSize(gridSize: Int) {
        if (gridSize > PITCH_HEIGHT / 2 || gridSize > PITCH_WIDTH)
            throw InvalidPitchGridSize("Grid size cannot be greater than 50% of length or width")
    }

    private fun isEventInGridCell(event: Event, gridX: Int, gridY: Int, gridSize: Int): Boolean {
        val location = event.location ?: return false

        return location.x >= gridX && location.x < gridX + gridSize
                && location.y >= gridY && location.y < gridY + gridSize
    }
}
