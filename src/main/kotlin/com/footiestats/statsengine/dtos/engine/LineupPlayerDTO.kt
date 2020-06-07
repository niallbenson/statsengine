package com.footiestats.statsengine.dtos.engine

data class LineupPlayerDTO(
        val id: Long,
        val name: String,
        val nickName: String?,
        val playerId: Long,
        val jerseyNumber: Int
)