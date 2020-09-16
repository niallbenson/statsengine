package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.getDisplayName
import com.footiestats.statsengine.services.engine.exceptions.ExpectedRelatedEventNotFound
import org.springframework.stereotype.Service

@Service
class BallReceiptAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.BALL_RECEIPT.id
    override val eventTypeName = "Ball Receipt"

    override fun eventDetail(event: Event): String? {
        return null
    }

    override fun isSuccessful(event: Event): Boolean {
        validateEventType(event)

        return event.ballReceipt?.outcome?.id == null
    }

    override fun outcome(event: Event): String {
        validateEventType(event)

        return if (isSuccessful(event))
            "Received from ${getReceivedPassFromPlayerName(event)}"
        else "Ball not received: ${event.ballReceipt?.outcome?.name}"
    }

    private fun getReceivedPassFromPlayerName(event: Event): String {
        return event.relatedEvents.find { it.type.id == EventTypeEnum.PASS.id }?.player?.getDisplayName()
                ?: throw ExpectedRelatedEventNotFound("Ball Receipt event should have a related Pass event")
    }

    override fun isKeyEvent(event: Event): Boolean {
        return false
    }

    override fun getEndLocation(event: Event): Location2D? = null
}
