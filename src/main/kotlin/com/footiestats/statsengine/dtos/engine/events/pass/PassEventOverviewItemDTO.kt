package com.footiestats.statsengine.dtos.engine.events.pass

import com.footiestats.statsengine.dtos.engine.Location2DDTO
import com.footiestats.statsengine.dtos.engine.TacticalLineupPlayerDTO

data class PassEventOverviewItemDTO(
        val eventId: Long,
        val teamId: Long,
        val player: TacticalLineupPlayerDTO,
        val type: String,
        val startLocation: Location2DDTO,
        val endLocation: Location2DDTO?,
        val successful: Boolean,
        val outcome: String
)
