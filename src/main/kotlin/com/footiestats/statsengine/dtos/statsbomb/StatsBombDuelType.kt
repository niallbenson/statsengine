package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

data class StatsBombDuelType(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String
)
