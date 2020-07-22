package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

data class StatsBombBodyPart(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String
)
