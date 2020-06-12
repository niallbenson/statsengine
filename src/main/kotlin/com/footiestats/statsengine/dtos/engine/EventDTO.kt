package com.footiestats.statsengine.dtos.engine

data class EventDTO(
        val id: Long,
        val player: PlayerDTO?,
        val eventType: String,
        val period: Int,
        val minute: Int,
        val second: Int,
        val eventTeam: TeamDTO,
        val possessionTeam: TeamDTO)