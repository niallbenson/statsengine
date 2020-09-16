package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.getDisplayName
import com.footiestats.statsengine.services.engine.exceptions.EventHasUnexpectedNullValue
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import org.springframework.stereotype.Service

@Service
class ShotAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.SHOT.id
    override val eventTypeName = "Shot"

    override fun eventDetail(event: Event): String? {
        return getShotDescription(event)
    }

    override fun isSuccessful(event: Event): Boolean {
        return isShotOnTarget(event)
    }

    override fun outcome(event: Event): String {
        return shotOutcome(event)
    }

    override fun isKeyEvent(event: Event): Boolean {
        return isShotOnTarget(event)
    }

    override fun getEndLocation(event: Event): Location2D? {
        val endLocation = event.shot?.endLocation
                ?: throw EventHasUnexpectedNullValue("Event with Shot type should have non-nul Shot")

        return Location2D(endLocation.x, endLocation.y)
    }

    fun isShotOnTarget(event: Event): Boolean {
        validateEventType(event)

        val onTargetOutcomeIds = arrayOf(
                OutcomeEnum.SAVED.id,
                OutcomeEnum.SAVED_TO_POST.id,
                OutcomeEnum.GOAL.id,
                OutcomeEnum.BLOCKED.id)

        return onTargetOutcomeIds.contains(event.shot?.outcome?.id)
    }

    fun isGoal(event: Event): Boolean {
        validateEventType(event)

        return event.shot?.outcome?.id == OutcomeEnum.GOAL.id
    }

    fun shotOutcome(event: Event): String {
        validateEventType(event)

        return when (event.shot?.outcome?.id) {
            OutcomeEnum.GOAL.id -> "Goal!!!"
            OutcomeEnum.BLOCKED.id -> "Blocked by ${getShotBlockedBy(event)}"
            OutcomeEnum.OFF_T.id -> "Off target"
            OutcomeEnum.SAVED.id -> "Saved"
            OutcomeEnum.POST.id -> "Hit the post"
            else -> "Shot"
        }
    }

    private fun getShotBlockedBy(event: Event): String {
        val blockEvent = event.relatedEvents.find { it.type.id == EventTypeEnum.BLOCK.id }

        return blockEvent?.player?.getDisplayName()
                ?: "unknown player"
    }

    private fun getShotDescription(event: Event): String {
        val bodyPart = getBodyPart(event)
        val technique = getTechnique(event)
        val shotType = getShotType(event)

        return "Shot with $bodyPart. $technique"
    }

    private fun getTechnique(event: Event): String {
        val shot = event.shot
                ?: throw EventHasUnexpectedNullValue("Shot events should have a shot")

        return shot.technique.name
    }

    private fun getBodyPart(event: Event): String {
        val shot = event.shot
                ?: throw EventHasUnexpectedNullValue("Shot events should have a shot")

        return shot.bodyPart.name
    }

    private fun getShotType(event: Event): String {
        val shot = event.shot
                ?: throw EventHasUnexpectedNullValue("Shot events should have a shot")

        return shot.shotType.name
    }

}
