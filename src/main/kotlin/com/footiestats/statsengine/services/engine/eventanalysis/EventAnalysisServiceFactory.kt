package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import org.springframework.context.ApplicationContext

class EventAnalysisServiceFactory(private val context: ApplicationContext) {

    fun getEventAnalysisService(event: Event): EventAnalysisService? {
        return when (event.type.id) {
            EventTypeEnum.BALL_RECEIPT.id -> getEventAnalysisServiceByName("ballReceipt")
            EventTypeEnum.CARRY.id -> getEventAnalysisServiceByName("carry")
            EventTypeEnum.PASS.id -> getEventAnalysisServiceByName("pass")
            EventTypeEnum.SHOT.id -> getEventAnalysisServiceByName("shot")
            EventTypeEnum.BALL_RECOVERY.id -> getEventAnalysisServiceByName("ballRecovery")
            EventTypeEnum.DRIBBLE.id -> getEventAnalysisServiceByName("dribble")
            else -> null
        }
    }

    private fun getEventAnalysisServiceByName(type: String): EventAnalysisService? {
        return context.getBean("${type}AnalysisService") as EventAnalysisService
    }

}
