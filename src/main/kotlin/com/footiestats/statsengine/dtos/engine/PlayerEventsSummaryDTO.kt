package com.footiestats.statsengine.dtos.engine

data class PlayerEventsSummaryDTO(
        val player: TacticalLineupPlayerDTO,
        val attackSummary: AttackingEventSummaryDTO
)