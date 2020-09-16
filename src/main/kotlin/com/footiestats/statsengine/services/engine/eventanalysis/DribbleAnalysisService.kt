package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import org.springframework.stereotype.Service

@Service
class DribbleAnalysisService : EventAnalysisService() {

    override val eventTypeId = EventTypeEnum.DRIBBLE.id
    override val eventTypeName = "Dribble"

    override fun eventDetail(event: Event): String? {
        return null
    }

    override fun isSuccessful(event: Event): Boolean {
        return event.dribble?.outcome?.id == OutcomeEnum.COMPLETE.id
    }

    override fun outcome(event: Event): String {
        return "Dribble " + event.dribble?.outcome?.name
    }

    override fun isKeyEvent(event: Event): Boolean {
        return false
    }

    override fun getEndLocation(event: Event): Location2D? {
        return null
    }


}
