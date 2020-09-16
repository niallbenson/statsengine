package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.services.engine.exceptions.EventHasUnexpectedNullValue
import org.springframework.stereotype.Service

@Service
class CarryAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.CARRY.id
    override val eventTypeName = "Carry"

    override fun eventDetail(event: Event): String? {
        return null
    }

    override fun isSuccessful(event: Event): Boolean {
        return true
    }

    override fun outcome(event: Event): String {
        return if (isMiscontrolled(event))
            "Miscontrolled"
        else "Ball carried ${getDistance(event, 2) ?: "UNKNOWN"}m"
    }

    private fun isMiscontrolled(event: Event): Boolean {
        return event.relatedEvents.find { it.type.id == EventTypeEnum.MISCONTROL.id } != null
    }

    override fun isKeyEvent(event: Event): Boolean {
        return false
    }

    override fun getEndLocation(event: Event): Location2D? {
        return event.carry?.endLocation
                ?: throw EventHasUnexpectedNullValue("Event with Carry type should have non-null Carry")
    }

}
