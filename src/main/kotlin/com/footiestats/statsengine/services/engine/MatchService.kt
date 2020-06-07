package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.MatchDTO
import com.footiestats.statsengine.dtos.engine.MatchLineupDTO
import com.footiestats.statsengine.dtos.engine.mappers.MatchMapper
import com.footiestats.statsengine.entities.engine.Season
import com.footiestats.statsengine.repos.engine.MatchRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun getMatchDto(id: Long): MatchDTO {
        val match = matchRepository.findByIdOrNull(id)
                ?: throw EntityNotFound("Match not found for $id")

        return MatchMapper.toDto(match)
    }

    fun getMatchDtos(competitionId: Long, seasonId: Long) =
            matchRepository.findAllByCompetitionAndSeason(competitionId, seasonId)
                    .sortedBy { it.matchDate }
                    .map { MatchMapper.toDto(it) }
                    .toTypedArray()

    fun getTeamLineupDto(matchId: Long, teamId: Long): MatchLineupDTO {
        val match = matchRepository.findByIdOrNull(matchId)
                ?: throw EntityNotFound("Match not found for $matchId")

        val matchLineup = match.lineups.find { it.team.id == teamId }
                ?: throw EntityNotFound("Match lineup not found for $matchId and $teamId")


    }
}