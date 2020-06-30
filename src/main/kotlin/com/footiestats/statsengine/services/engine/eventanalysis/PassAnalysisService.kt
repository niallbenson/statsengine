package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import org.springframework.stereotype.Service

@Service
class PassAnalysisService {

    fun isPassAccurate(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.outcome == null
                && event.relatedEvents.find { r -> r.type.id == EventTypeEnum.BALL_RECEIPT.id } != null
    }

    fun passOutcome(event: Event): String {
        validateEventType(event)

        return "Pass outcome TBC"
    }

    fun isAssist(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.assistedShot?.shot?.outcome?.id == OutcomeEnum.GOAL.id
    }

    fun isKeyPass(event: Event): Boolean {
        validateEventType(event)

        return event.pass?.assistedShot != null
    }

    private fun validateEventType(event: Event) {
        if (event.type.id != EventTypeEnum.PASS.id)
            throw UnexpectedEventType("Expected Pass event, got ${event.type.name}")
    }
}