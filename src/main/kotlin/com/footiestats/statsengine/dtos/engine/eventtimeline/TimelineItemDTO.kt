package com.footiestats.statsengine.dtos.engine.eventtimeline

data class TimelineItemDTO(
        val eventId: Long,
        val period: Int,
        val minute: Int,
        val second: Int,
        val type: String,
        val successful: Boolean,
        val outcome: String,
        val key: Boolean
)