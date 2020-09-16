package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import kotlin.math.sqrt

abstract class EventAnalysisService {

    abstract val eventTypeName: String
    abstract val eventTypeId: Long

    protected fun validateEventType(event: Event) {
        if (event.type.id != eventTypeId)
            throw UnexpectedEventType("Expected $eventTypeName event, got ${event.type.name}")
    }

    abstract fun eventDetail(event: Event): String?
    abstract fun isSuccessful(event: Event): Boolean?
    abstract fun outcome(event: Event): String
    abstract fun isKeyEvent(event: Event): Boolean

    fun getDistance(event: Event): Double? {
        val startLocation = event.location
                ?: return null

        val endLocation = getEndLocation(event)
                ?: return null

        val xDist = endLocation.x - startLocation.x
        val yDist = endLocation.y - startLocation.y

        return sqrt(xDist * xDist + yDist * yDist)
    }

    fun getDistance(event: Event, decimals: Int): Double? {
        val distance = getDistance(event)

        return if (distance != null)
            "%.${decimals}f".format(distance).toDouble()
        else null
    }

    protected abstract fun getEndLocation(event: Event): Location2D?

}
