package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

data class StatsBombShotType(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String
)
