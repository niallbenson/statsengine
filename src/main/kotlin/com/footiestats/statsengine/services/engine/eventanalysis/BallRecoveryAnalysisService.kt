package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.getDisplayName
import org.springframework.stereotype.Service

@Service
class BallRecoveryAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.BALL_RECOVERY.id
    override val eventTypeName = "Ball Recovery"

    override fun eventDetail(event: Event): String? {
        return null
    }

    override fun isSuccessful(event: Event): Boolean {
        return true
    }

    override fun outcome(event: Event): String {
        val recoveredFromEvent = getRecoveredFromEvent(event)

        return "Recovered from ${recoveredFromEvent?.player?.getDisplayName() ?: "loose ball"}"
    }

    private fun getRecoveredFromEvent(event: Event) =
            event.relatedEvents.find { it.type.id == EventTypeEnum.PASS.id || it.type.id == EventTypeEnum.PRESSURE.id }

    override fun isKeyEvent(event: Event): Boolean {
        return false
    }

    override fun getEndLocation(event: Event): Location2D? {
        return null
    }


}
