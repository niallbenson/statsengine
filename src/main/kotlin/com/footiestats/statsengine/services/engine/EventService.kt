package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.EventDTO
import com.footiestats.statsengine.dtos.engine.EventDetailListItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.EventMapper
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.LineupPlayerRepository
import com.footiestats.statsengine.repos.engine.MatchRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
        private val eventRepository: EventRepository,
        private val matchRepository: MatchRepository,
        private val eventMapper: EventMapper,
        private val lineupPlayerRepository: LineupPlayerRepository
) {

    fun getPlayerMatchEvents(playerId: Long, matchId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("playerId $playerId is invalid")

        return eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

    fun getMatchEventsByType(matchId: Long, eventTypeId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (eventTypeId < 1) throw EntityIdMustBeGreaterThanZero("eventTypeId $eventTypeId is invalid")

        return eventRepository.findAllByMatch_IdAndType_Id(matchId, eventTypeId)
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

    fun getMatchTeamGoals(matchId: Long, teamId: Long): Array<EventDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (teamId < 1) throw EntityIdMustBeGreaterThanZero("teamId $teamId is invalid")

        val shotsWithGoalOutcomes =
                eventRepository.findAllByMatch_IdAndType_IdAndShot_Outcome_IdAndEventTeam_IdOrderByEventIndex(
                        matchId, EventTypeEnum.SHOT.id, OutcomeEnum.GOAL.id, teamId)

        val match = matchRepository.findByIdOrNull(matchId) ?: throw EntityNotFound("Match $matchId not found")

        val opposingTeamId = (if (match.homeTeam.id == teamId) match.awayTeam.id else match.homeTeam.id)
                ?: throw EntityNotFound("Opposing team not found for match $matchId")

        val ownGoalsFor = eventRepository.findAllByMatch_IdAndType_IdAndEventTeam_Id(matchId,
                EventTypeEnum.OWN_GOAL_AGAINST.id, opposingTeamId)

        return (shotsWithGoalOutcomes + ownGoalsFor)
                .sortedBy { it.eventIndex }
                .map { eventMapper.toDto(it) }
                .toTypedArray()
    }

    fun getMatchEventDetailListItemDtos(matchId: Long): Array<EventDetailListItemDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")

        val events = eventRepository.findAllByMatch_Id(matchId)
        val lineupPlayers = lineupPlayerRepository.findAllByMatchLineup_Match_Id(matchId)
                .map { it.player.id to it }.toMap()

        return events
                .sortedBy { it.eventIndex }
                .map { eventMapper.toListItemDetailDto(it, lineupPlayers)}
                .toTypedArray()
    }

    fun getPlayerMatchEventDetailListItemDtos(matchId: Long, playerId: Long): Array<EventDetailListItemDTO> {
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("matchId $matchId is invalid")
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("playerId $playerId is invalid")

        val events = eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
        val lineupPlayers = lineupPlayerRepository.findAllByMatchLineup_Match_Id(matchId) // TODO: should only return the one lineup player here
                .map { it.player.id to it }.toMap()

        return events
                .sortedBy { it.eventIndex }
                .map { eventMapper.toListItemDetailDto(it, lineupPlayers)}
                .toTypedArray()
    }

}
