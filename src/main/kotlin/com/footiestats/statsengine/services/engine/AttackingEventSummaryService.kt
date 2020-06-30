package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.AttackingEventSummaryDTO
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import com.footiestats.statsengine.services.engine.eventanalysis.ShotAnalysisService
import org.springframework.stereotype.Service

@Service
class AttackingEventSummaryService(
        private val eventRepository: EventRepository,
        private val shotAnalysisService: ShotAnalysisService,
        private val passAnalysisService: PassAnalysisService
) {

    fun getPlayerMatchEventsTypeIdMap(playerId: Long, matchId: Long): Map<Long, MutableSet<Event>> {
        if (playerId < 1) throw EntityIdMustBeGreaterThanZero("$playerId playerId is invalid")
        if (matchId < 1) throw EntityIdMustBeGreaterThanZero("$matchId matchId is invalid")

        return eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId)
                .groupBy { it.type.id!! }
                .mapValues { e -> e.value.toMutableSet() }
    }

    fun getPlayerMatchAttackSummaryDto(playerId: Long, matchId: Long): AttackingEventSummaryDTO {
        val typesMap = getPlayerMatchEventsTypeIdMap(playerId, matchId)

        return AttackingEventSummaryDTO(
                getGoalCount(typesMap),
                getTotalShots(typesMap),
                getShotsOnTarget(typesMap),
                getTotalPasses(typesMap),
                getAccuratePasses(typesMap),
                getKeyPasses(typesMap),
                getAssists(typesMap)
        )
    }

    private fun getGoalCount(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.SHOT.id]
                ?.filter { shotAnalysisService.isGoal(it) }
                ?.count() ?: 0
    }

    private fun getTotalShots(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.SHOT.id]
                ?.count() ?: 0
    }

    private fun getShotsOnTarget(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.SHOT.id]
                ?.filter { shotAnalysisService.isShotOnTarget(it) }
                ?.count() ?: 0
    }

    private fun getTotalPasses(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.PASS.id]
                ?.count() ?: 0
    }

    private fun getAccuratePasses(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.PASS.id]
                ?.filter { passAnalysisService.isPassAccurate(it) }
                ?.count() ?: 0
    }

    private fun getKeyPasses(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.PASS.id]
                ?.filter { passAnalysisService.isKeyPass(it) }
                ?.count() ?: 0
    }

    private fun getAssists(typeEventMap: Map<Long, MutableSet<Event>>): Int {
        return typeEventMap[EventTypeEnum.PASS.id]
                ?.filter { passAnalysisService.isAssist(it) }
                ?.count() ?: 0
    }

}