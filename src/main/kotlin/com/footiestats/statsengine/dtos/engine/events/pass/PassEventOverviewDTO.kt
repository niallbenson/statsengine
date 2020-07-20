package com.footiestats.statsengine.dtos.engine.events.pass

data class PassEventOverviewDTO(
        val eventId: Long,
        val matchId: Long,
        val period: Int,
        val minute: Int,
        val second: Int,
        val previousEvent: PassEventOverviewItemDTO,
        val pass: PassEventOverviewItemDTO,
        val target: PassEventOverviewItemDTO?,
        val nextEvent: PassEventOverviewItemDTO
)
