package com.footiestats.statsengine.dtos.engine

data class TacticalLineupPlayerDTO(
        val id: Long,
        val name: String,
        val nickName: String?,
        val playerId: Long,
        val jerseyNumber: Int,
        val positionId: Long,
        val position: String
)