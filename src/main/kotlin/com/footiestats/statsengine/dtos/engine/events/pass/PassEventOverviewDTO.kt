package com.footiestats.statsengine.dtos.engine.events.pass

data class PassEventOverviewDTO(
        val eventId: Long,
        val previousEvent: PassEventOverviewItemDTO,
        val pass: PassEventOverviewItemDTO,
        val target: PassEventOverviewItemDTO,
        val nextEvent: PassEventOverviewItemDTO
)
