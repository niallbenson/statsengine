package com.footiestats.statsengine.dtos.engine;

data class EventDetailListItemDTO(
        val id: Long,
        val playPattern: String,
        val possessionTeam: TeamDTO,
        val eventTeam: TeamDTO,
        val player: PlayerDTO?,
        val jerseyNumber: Int?,
        val playerPosition: String?,
        val eventType: String,
        val outcome: String,
        val distance: Double?,
        val period: Int,
        val minute: Int,
        val second: Int
)
