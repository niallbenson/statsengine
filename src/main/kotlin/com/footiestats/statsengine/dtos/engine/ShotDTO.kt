package com.footiestats.statsengine.dtos.engine

data class ShotDTO(
        val event: EventDTO,
        val type: String,
        val outcome: String,
        val bodyPart: String,
        val technique: String,
        val keyPassPlayer: PlayerDTO?
)