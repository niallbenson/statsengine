package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.events.Event
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class UberEventAnalysisService(context: ApplicationContext) {

    val eventAnalysisServiceFactory = EventAnalysisServiceFactory(context)

    fun isEventSuccessful(event: Event): Boolean? {
        val eventAnalysisService = eventAnalysisServiceFactory.getEventAnalysisService(event)

        return eventAnalysisService?.isSuccessful(event)
    }

    fun getOutcome(event: Event): String {
        val eventAnalysisService = eventAnalysisServiceFactory.getEventAnalysisService(event)

        return eventAnalysisService?.outcome(event) ?: ""
    }

    fun isKeyEvent(event: Event): Boolean {
        val eventAnalysisService = eventAnalysisServiceFactory.getEventAnalysisService(event)

        return eventAnalysisService?.isKeyEvent(event) ?: false
    }

    fun getDistance(event: Event): Double? {
        val eventAnalysisService = eventAnalysisServiceFactory.getEventAnalysisService(event)

        return eventAnalysisService?.getDistance(event)
    }

    fun getEventDetail(event: Event): String? {
        val eventAnalysisService = eventAnalysisServiceFactory.getEventAnalysisService(event)

        return eventAnalysisService?.eventDetail(event)
    }

}
