package com.footiestats.statsengine.dtos.engine

class TacticsDTO(
        val id: Long,
        val event: EventDTO,
        val formation: Int,
        val players: Array<TacticalLineupPlayerDTO>
)