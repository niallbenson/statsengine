package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.MatchLineupDTO
import com.footiestats.statsengine.entities.engine.MatchLineup

class MatchLineupMapper {
    companion object {
        fun toDto(matchLineup: MatchLineup): MatchLineupDTO {
            return MatchLineupDTO(
                    matchLineup.id ?: -1,
                    TeamMapper.toDto(matchLineup.team),
                    matchLineup.players
                            .map { LineupPlayerMapper.toDto(it) }
                            .toTypedArray()
            )
        }
    }
}