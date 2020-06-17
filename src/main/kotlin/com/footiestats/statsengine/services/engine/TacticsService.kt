package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.TacticsDTO
import com.footiestats.statsengine.dtos.engine.mappers.TacticsMapper
import com.footiestats.statsengine.repos.engine.TacticsRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import org.springframework.stereotype.Service

@Service
class TacticsService(
        private val tacticsRepository: TacticsRepository,
        private val tacticsMapper: TacticsMapper
) {

    fun getMatchTeamTacticsDto(matchId: Long, teamId: Long): Array<TacticsDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (teamId < 1) throw EntityIdMustBeGreaterThanZero("teamId $teamId is invalid")

        val tactics = tacticsRepository.findAllByEvent_Match_IdAndEvent_EventTeam_Id(matchId, teamId)

        return tactics
                .sortedBy { it.event.eventIndex }
                .map { tacticsMapper.toDto(it) }
                .toTypedArray()
    }
}