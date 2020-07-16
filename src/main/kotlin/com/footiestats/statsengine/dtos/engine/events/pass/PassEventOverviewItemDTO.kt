package com.footiestats.statsengine.dtos.engine.events.pass

import com.footiestats.statsengine.dtos.engine.Location2DDTO
import com.footiestats.statsengine.dtos.engine.PlayerDTO

data class PassEventOverviewItemDTO(
        val eventId: Long,
        val player: PlayerDTO?,
        val type: String,
        val startLocation: Location2DDTO,
        val endLocation: Location2DDTO,
        val successful: Boolean,
        val outcome: String
)
