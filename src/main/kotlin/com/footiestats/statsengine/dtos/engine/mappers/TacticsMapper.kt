package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.TacticsDTO
import com.footiestats.statsengine.entities.engine.events.metadata.Tactics
import org.springframework.stereotype.Service

@Service
class TacticsMapper(private val tacticalLineupPlayerMapper: TacticalLineupPlayerMapper) {

    fun toDto(tactics: Tactics): TacticsDTO {

        return TacticsDTO(
                tactics.id ?: -1,
                tactics.formation,
                tactics.lineup.map { tacticalLineupPlayerMapper.toDto(it) }.toTypedArray()
        )
    }
}