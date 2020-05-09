package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombManager(
        @JsonAlias("id") var id: Long,
        @JsonAlias("name") var name: String,
        @JsonAlias("nickname") var nickname: String,
        @JsonAlias("dob") var dob: String,
        @JsonAlias("country") var country: StatsBombCountry
)