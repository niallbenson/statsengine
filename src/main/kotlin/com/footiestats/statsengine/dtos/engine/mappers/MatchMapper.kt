package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.MatchDTO
import com.footiestats.statsengine.entities.engine.Match

class MatchMapper {
    companion object {
        fun toDto(match: Match): MatchDTO {
            return MatchDTO(
                    match.id ?: -1,
                    match.homeTeam.name,
                    match.awayTeam.name,
                    match.matchDate,
                    match.matchStatus,
                    match.homeScore,
                    match.awayScore,
                    match.competitionStage.name)
        }
    }
}