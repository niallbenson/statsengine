package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.EventDTO
import com.footiestats.statsengine.dtos.engine.EventDetailListItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.exceptions.ExpectedEntityNotFound
import com.footiestats.statsengine.entities.engine.LineupPlayer
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.services.engine.eventanalysis.EventAnalysisService
import org.springframework.stereotype.Service

@Service
class EventMapper(
        private val playerMapper: PlayerMapper,
        private val teamMapper: TeamMapper,
        private val eventAnalysisService: EventAnalysisService
) {

    fun toDto(event: Event): EventDTO {
        val player =
                if (event.player != null)
                    playerMapper.toDto(event.player!!)
                else null

        return EventDTO(
                event.id ?: -1,
                player,
                event.type.name,
                event.period,
                event.minute,
                event.second,
                teamMapper.toDto(event.eventTeam),
                teamMapper.toDto(event.possessionTeam)
        )
    }

    fun toListItemDetailDto(event: Event,
                            lineupPlayers: Map<Long?, LineupPlayer>): EventDetailListItemDTO {
        val playerDto =
                if (event.player != null)
                    playerMapper.toDto(event.player!!)
                else null

        return EventDetailListItemDTO(
                event.id ?: -1,
                event.playPattern.name,
                teamMapper.toDto(event.possessionTeam),
                teamMapper.toDto(event.eventTeam),
                playerDto,
                getJerseyNumber(event.player, lineupPlayers),
                event.position?.name,
                event.type.name,
                eventAnalysisService.getOutcome(event),
                eventAnalysisService.getDistance(event),
                event.period,
                event.minute,
                event.second)
    }

    fun getJerseyNumber(player: Player?,
                        lineupPlayers: Map<Long?, LineupPlayer>): Int? {
        if (player == null) return null

        val lineupPlayer = lineupPlayers[player.id]
                ?: throw ExpectedEntityNotFound("${player.id} not found in lineup players")

        return lineupPlayer.jerseyNumber
    }

}