package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.services.engine.exceptions.EventHasUnexpectedNullValue
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import org.springframework.stereotype.Service
import kotlin.math.sqrt

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

    fun getDistance(event: Event): Double? {
        val startLocation = event.location
                ?: return null

        val endLocation = getEndLocation(event)
                ?: return null

        val xDist = endLocation.x - startLocation.x
        val yDist = endLocation.y - startLocation.y

        return sqrt(xDist * xDist + yDist * yDist)
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

    private fun getEndLocation(event: Event): Location2D? {
        return when (event.type.id) {
            EventTypeEnum.SHOT.id -> getShotEndLocationAs2D(event)
            EventTypeEnum.PASS.id -> event.pass?.endLocation
                    ?: throw EventHasUnexpectedNullValue("Event with Pass type should have non-null Pass")
            EventTypeEnum.CARRY.id -> event.carry?.endLocation
                    ?: throw EventHasUnexpectedNullValue("Event with Carry type should have non-null Carry")
            else -> null
        }
    }

    private fun getShotEndLocationAs2D(event: Event): Location2D {
        val endLocation = event.shot?.endLocation
                ?: throw EventHasUnexpectedNullValue("Event with Shot type should have non-nul Shot")

        return Location2D(endLocation.x, endLocation.y)
    }

    private fun validateEventType(event: Event, expectedType: EventTypeEnum) {
        if (event.type.id != expectedType.id)
            throw UnexpectedEventType("Expected ${expectedType.name}, got ${event.type.name}")
    }
}
