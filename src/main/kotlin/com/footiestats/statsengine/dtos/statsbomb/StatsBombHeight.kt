package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombHeight(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String
)