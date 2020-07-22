package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewDTO
import com.footiestats.statsengine.dtos.engine.events.pass.PassEventOverviewItemDTO
import com.footiestats.statsengine.dtos.engine.mappers.PassEventOverviewItemMapper
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.TacticalLineupPlayer
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.LineupPlayerRepository
import com.footiestats.statsengine.repos.engine.SubstitutionRepository
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import com.footiestats.statsengine.services.engine.exceptions.ExpectedRelatedEventNotFound
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PassService(
        private val eventRepository: EventRepository,
        private val passEventOverviewItemMapper: PassEventOverviewItemMapper,
        private val passAnalysisService: PassAnalysisService,
        private val substitutionRepository: SubstitutionRepository,
        private val lineupPlayerRepository: LineupPlayerRepository
) {

    fun getPassEventOverviewDto(eventId: Long): PassEventOverviewDTO {
        if (eventId < 1) throw EntityIdMustBeGreaterThanZero("eventId $eventId is invalid")

        val passEvent = eventRepository.findByIdOrNull(eventId)
                ?: throw EntityNotFound("Pass event not found for $eventId")

        val passEventDto = getOverviewItemDTOForPassEvent(passEvent)

        val previousEventDto = getPreviousEventDto(passEvent)

        val target = passEvent.relatedEvents.find { it.type.id == EventTypeEnum.BALL_RECEIPT.id }
        val targetDto = getTargetDto(target)

        val nextEvenDto = getNextEventDto(passEvent, target)

        return PassEventOverviewDTO(
                eventId,
                passEvent.match.id ?: -1,
                passEvent.period,
                passEvent.minute,
                passEvent.second,
                previousEventDto,
                passEventDto,
                targetDto,
                nextEvenDto
        )
    }

    private fun getPreviousEventDto(passEvent: Event): PassEventOverviewItemDTO {
        val event = eventRepository.findByMatch_IdAndEventIndex(
                passEvent.match.id!!, passEvent.eventIndex - 1)

        return getOverviewItemDTOForAnyEventType(event)
    }

    private fun getLineupPlayer(event: Event): TacticalLineupPlayer {
        val lineupPlayerResults = getLineupPlayerResults(event, event.player!!)

        return if (lineupPlayerResults.isNotEmpty()) {
            lineupPlayerResults[0]
        } else {
            getLineupPlayerForReplacedPlayer(event)
        }
    }

    private fun getLineupPlayerForReplacedPlayer(event: Event): TacticalLineupPlayer {
        val eventPlayer = event.player
                ?: throw EntityNotFound("Expected event ${event.id} to have a player")

        val replacedPlayer = getSubstituteReplacedPlayer(event.match, eventPlayer)

        val replacedPlayerLineupResults = getLineupPlayerResults(event, replacedPlayer)

        if (replacedPlayerLineupResults.isEmpty())
            throw EntityNotFound("No tactical lineup player found for event ${event.id}")

        val lineupPlayer = lineupPlayerRepository.findByPlayerAndMatchLineup_Match(eventPlayer, event.match)

        val tacticalLineupForReplaced = replacedPlayerLineupResults[0]

        return tacticalLineupForReplaced.copy(player = eventPlayer, jerseyNumber = lineupPlayer.jerseyNumber)
    }

    private fun getLineupPlayerResults(event: Event, player: Player) =
            eventRepository.getTacticalLineupPlayerAtEventIndex(
                    event.match.id ?: -1, player.id ?: -1, event.eventIndex,
                    EventTypeEnum.STARTING_XI.id, EventTypeEnum.TACTICAL_SHIFT.id,
                    PageRequest.of(0, 1))

    private fun getSubstituteReplacedPlayer(match: Match, replacement: Player): Player {
        val substitution = substitutionRepository.findByEvent_MatchAndReplacement(match, replacement)

        return substitution.event?.player
                ?: throw EntityNotFound("Replaced player for ${replacement.id} not found in match ${match.id}")
    }

    private fun getNextEventDto(passEvent: Event, target: Event?): PassEventOverviewItemDTO {
        val event = if (passAnalysisService.isPassAccurate(passEvent) && target != null) {
            target
        } else getNextEventForInaccuratePass(passEvent)

        return getOverviewItemDTOForAnyEventType(event)
    }

    private fun getTargetDto(target: Event?): PassEventOverviewItemDTO? {
        return if (target != null) {
            getOverviewItemDTOForAnyEventType(target)
        } else null
    }

    private fun getNextEventForInaccuratePass(passEvent: Event) =
            passEvent.relatedEvents.find {
                it.type.id != EventTypeEnum.BALL_RECEIPT.id && it.eventIndex > passEvent.eventIndex
            } ?: throw ExpectedRelatedEventNotFound("Inaccurate Pass has no next event")

    private fun getOverviewItemDTOForPassEvent(event: Event): PassEventOverviewItemDTO {
        val lineupPlayer = getLineupPlayer(event)

        return passEventOverviewItemMapper.toDtoFromPassEvent(event, lineupPlayer)
    }

    private fun getOverviewItemDTOForAnyEventType(event: Event): PassEventOverviewItemDTO {
        val lineupPlayer = getLineupPlayer(event)

        return passEventOverviewItemMapper.toDtoFromAnyEventType(event, lineupPlayer)
    }
}
