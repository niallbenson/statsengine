package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.eventtimeline.PlayerMatchTimelineDTO
import com.footiestats.statsengine.dtos.engine.eventtimeline.TimelineItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.PlayerMapper
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.PlayerRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import com.footiestats.statsengine.services.engine.eventanalysis.EventAnalysisService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlayerMatchTimelineService(
        private val playerRepository: PlayerRepository,
        private val eventRepository: EventRepository,
        private val playerMapper: PlayerMapper,
        private val eventAnalysisService: EventAnalysisService
) {

    fun getPlayerMatchTimelineDto(playerId: Long, matchId: Long): PlayerMatchTimelineDTO {
        validateEntityIds(playerId, matchId)

        val player = getPlayer(playerId)

        val events = getEvents(playerId, matchId)

        return PlayerMatchTimelineDTO(
                playerMapper.toDto(player),
                events.map { mapTimelineItem(it) }.toTypedArray()
        )
    }

    fun getPlayerMatchTimelineKeyEventsDto(playerId: Long, matchId: Long): PlayerMatchTimelineDTO {
        validateEntityIds(playerId, matchId)

        val player = getPlayer(playerId)

        val events = getEvents(playerId, matchId)
                .filter { eventAnalysisService.isKeyEvent(it) }
                .toTypedArray()

        return PlayerMatchTimelineDTO(
                playerMapper.toDto(player),
                events.map { mapTimelineItem(it) }.toTypedArray()
        )
    }

    private fun getEvents(playerId: Long, matchId: Long) =
            eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
                    .sortedBy { it.eventIndex }

    private fun getPlayer(playerId: Long) = playerRepository.findByIdOrNull(playerId)
            ?: throw EntityNotFound("Player $playerId not found")

    private fun validateEntityIds(playerId: Long, matchId: Long) {
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("$playerId playerId is invalid")
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("$matchId matchId is invalid")
    }

    private fun mapTimelineItem(event: Event) = TimelineItemDTO(
            event.id ?: -1,
            event.period,
            event.minute,
            event.second,
            event.type.name,
            eventAnalysisService.isEventSuccessful(event),
            eventAnalysisService.getOutcome(event),
            eventAnalysisService.isKeyEvent(event)
    )

}