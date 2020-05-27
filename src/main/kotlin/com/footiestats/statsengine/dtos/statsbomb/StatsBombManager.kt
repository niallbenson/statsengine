package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombManager(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String,
        @JsonAlias("nickname") val nickname: String?,
        @JsonAlias("dob") val dob: String?,
        @JsonAlias("country") val country: StatsBombCountry
)