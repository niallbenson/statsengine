package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.MatchLineupDTO
import com.footiestats.statsengine.entities.engine.MatchLineup
import org.springframework.stereotype.Service

@Service
class MatchLineupMapper(
        private val lineupPlayerMapper: LineupPlayerMapper,
        private val teamMapper: TeamMapper
) {

    fun toDto(matchLineup: MatchLineup): MatchLineupDTO {
        return MatchLineupDTO(
                matchLineup.id ?: -1,
                teamMapper.toDto(matchLineup.team),
                matchLineup.players
                        .map { lineupPlayerMapper.toDto(it) }
                        .toTypedArray()
        )
    }
}