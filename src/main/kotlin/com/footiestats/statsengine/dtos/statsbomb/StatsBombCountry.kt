package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCountry(
        @JsonAlias("id") var id: Long,
        @JsonAlias("name") var name: String
)