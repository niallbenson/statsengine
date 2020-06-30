package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import org.springframework.stereotype.Service

@Service
class ShotAnalysisService {

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

        return "Shot outcome TBC"
    }

    private fun validateEventType(event: Event) {
        if (event.type.id != EventTypeEnum.SHOT.id)
            throw UnexpectedEventType("Expected Shot event, got ${event.type.name}")
    }

}