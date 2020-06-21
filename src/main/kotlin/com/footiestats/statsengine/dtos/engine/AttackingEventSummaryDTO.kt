package com.footiestats.statsengine.dtos.engine

data class AttackingEventSummaryDTO(
        val goals: Int,
        val totalShots: Int,
        val shotsOnTarget: Int,
        val totalPasses: Int,
        val accuratePasses: Int,
        val totalKeyPasses: Int,
        val assists: Int
)