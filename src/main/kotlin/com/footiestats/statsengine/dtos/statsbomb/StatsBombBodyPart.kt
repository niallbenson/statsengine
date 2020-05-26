package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombBodyPart(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String
)