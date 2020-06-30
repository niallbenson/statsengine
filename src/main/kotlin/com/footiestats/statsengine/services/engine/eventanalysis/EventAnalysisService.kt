package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.services.engine.ShotService
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import org.springframework.stereotype.Service

@Service
class EventAnalysisService(
        private val shotAnalysisService: ShotAnalysisService,
        private val passAnalysisService: PassAnalysisService
) {

    fun isEventSuccessful(event: Event): Boolean {
        return when (event.type.id) {
            EventTypeEnum.SHOT.id -> return shotAnalysisService.isShotOnTarget(event)
            EventTypeEnum.PASS.id -> return passAnalysisService.isPassAccurate(event)
            else -> false
        }
    }

    fun getOutcome(event: Event): String {
        return when (event.type.id) {
            EventTypeEnum.SHOT.id -> return shotAnalysisService.shotOutcome(event)
            EventTypeEnum.PASS.id -> return passAnalysisService.passOutcome(event)
            else -> ""
        }
    }

    fun isKeyEvent(event: Event): Boolean {
        return when (event.type.id) {
            EventTypeEnum.SHOT.id -> return isShotKeyEvent(event)
            EventTypeEnum.PASS.id -> return isPassKeyEvent(event)
            else -> false
        }
    }

    private fun isShotKeyEvent(event: Event): Boolean {
        validateEventType(event, EventTypeEnum.SHOT)

        return shotAnalysisService.isShotOnTarget(event)
    }

    private fun isPassKeyEvent(event: Event): Boolean {
        validateEventType(event, EventTypeEnum.PASS)

        return passAnalysisService.isKeyPass(event)
                || passAnalysisService.isAssist(event)
    }

    private fun validateEventType(event: Event, expectedType: EventTypeEnum) {
        if (event.type.id != expectedType.id)
            throw UnexpectedEventType("Expected ${expectedType.name}, got ${event.type.name}")
    }
}