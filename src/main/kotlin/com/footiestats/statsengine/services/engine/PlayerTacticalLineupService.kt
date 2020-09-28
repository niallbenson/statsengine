package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.TacticalLineupPlayerDTO
import com.footiestats.statsengine.dtos.engine.mappers.TacticalLineupPlayerMapper
import com.footiestats.statsengine.repos.engine.TacticalLineupPlayerRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PlayerTacticalLineupService(
        private val tacticalLineupPlayerRepository: TacticalLineupPlayerRepository,
        private val tacticalLineupPlayerMapper: TacticalLineupPlayerMapper
) {

    fun getFirstInstanceOfPlayerMatchTacticalLineup(playerId: Long, matchId: Long): TacticalLineupPlayerDTO {
        val lineupPlayer = tacticalLineupPlayerRepository.getPlayerMatchTacticalLineups(playerId, matchId,  PageRequest.of(0, 1))

        if (lineupPlayer.isEmpty()) throw EntityNotFound("No instance of lineup player found for $playerId $matchId")

        return tacticalLineupPlayerMapper.toDto(lineupPlayer[0])
    }

}
