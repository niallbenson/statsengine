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
class PassAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.PASS.id
    override val eventTypeName = "Pass"

    override fun eventDetail(event: Event): String? {
        val passHeight = getPassHeightName(event)
        val bodyPart = getBodyPart(event)

        return passHeight + if (bodyPart != null) " with $bodyPart" else ""
    }

    override fun isSuccessful(event: Event): Boolean {
        return isPassAccurate(event)
    }

    override fun isKeyEvent(event: Event): Boolean {
        return isKeyPass(event)
    }

    override fun outcome(event: Event): String {
        validateEventType(event)

        return if (isPassAccurate(event))
            getAccuratePassDescription(event)
        else getInaccuratePassDescription(event)
    }

    override fun getEndLocation(event: Event): Location2D? {
        return event.pass?.endLocation
                ?: throw EventHasUnexpectedNullValue("Event with Pass type should have non-null Pass")
    }

    fun isPassAccurate(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.outcome == null
                && event.relatedEvents.find { r -> r.type.id == EventTypeEnum.BALL_RECEIPT.id } != null
    }

    private fun getAccuratePassDescription(event: Event): String {
        return when {
            isAssist(event) -> "Assist!! Scored by " + (event.pass?.assistedShot?.player?.getDisplayName() ?: "scorer unknown")
            isKeyPass(event) -> "Goal scoring opportunity created for " + (event.pass?.assistedShot?.player?.getDisplayName() ?: "player unknown")
            else -> return "Received by " + getPassRecipient(event)
        }
    }

    private fun getInaccuratePassDescription(event: Event): String {
        val outcome = event.pass?.outcome?.name
                ?: "Inaccurate"

        val ballRecoveryEvent = event.relatedEvents.find { it.type.id == EventTypeEnum.BALL_RECOVERY.id }

        return if (ballRecoveryEvent != null)
            "$outcome, recovered by ${ballRecoveryEvent.player?.getDisplayName() ?: "player unknown"}"
        else outcome
    }

    private fun getPassRecipient(event: Event): String {
        val recipientEvent = event.relatedEvents.find { it.type.id == EventTypeEnum.BALL_RECEIPT.id }
                ?: return "unknown recipient"

        return recipientEvent.player?.getDisplayName()
                ?: return "unknown recipient"
    }

    fun isAssist(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.assistedShot?.shot?.outcome?.id == OutcomeEnum.GOAL.id
    }

    fun isKeyPass(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.assistedShot != null
    }

    private fun getPassHeightName(event: Event): String {
        val pass = event.pass
                ?: throw EventHasUnexpectedNullValue("Pass event type should have a Pass")

        return pass.passHeight.name
    }

    private fun getBodyPart(event: Event): String? {
        val pass = event.pass
                ?: throw EventHasUnexpectedNullValue("Pass event type should have a Pass")

        return pass.bodyPart?.name
    }

}
