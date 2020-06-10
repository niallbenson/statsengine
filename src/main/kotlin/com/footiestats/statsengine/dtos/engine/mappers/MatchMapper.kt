package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.MatchDTO
import com.footiestats.statsengine.entities.engine.Match
import org.springframework.stereotype.Service

@Service
class MatchMapper {

    fun toDto(match: Match): MatchDTO {
        return MatchDTO(
                match.id ?: -1,
                match.homeTeam.name,
                match.awayTeam.name,
                match.homeTeam.id ?: -1,
                match.awayTeam.id ?: -1,
                match.matchDate,
                match.matchStatus,
                match.homeScore,
                match.awayScore,
                match.competitionStage.name)
    }
}