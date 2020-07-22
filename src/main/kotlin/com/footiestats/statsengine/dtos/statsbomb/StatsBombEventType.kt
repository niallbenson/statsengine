package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

data class StatsBombEventType(
        @JsonAlias("id") val id: Int,
        @JsonAlias("name") val name: String
)
