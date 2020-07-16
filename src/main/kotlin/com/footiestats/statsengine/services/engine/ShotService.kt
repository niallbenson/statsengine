package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.ShotDTO
import com.footiestats.statsengine.dtos.engine.mappers.ShotMapper
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import org.springframework.stereotype.Service

@Service
class ShotService(
        private val eventRepository: EventRepository,
        private val shotMapper: ShotMapper
) {

    fun getMatchTeamShotsDtos(matchId: Long, teamId: Long): Array<ShotDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (teamId < 1) throw EntityIdMustBeGreaterThanZero("teamId $teamId is invalid")

        return eventRepository.findAllByMatch_IdAndType_IdAndEventTeam_Id(
                matchId, EventTypeEnum.SHOT.id, teamId)
                .sortedBy { it.eventIndex }
                .map { shotMapper.toDto(it) }
                .toTypedArray()
    }

}
