package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombStadium(
        @JsonAlias("id") val id: Long,
        @JsonAlias("name") val name: String,
        @JsonAlias("country") val country: StatsBombCountry)