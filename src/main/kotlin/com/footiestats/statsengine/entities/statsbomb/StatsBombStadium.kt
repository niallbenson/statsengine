package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombStadium(
        @JsonAlias("id") var id: Long,
        @JsonAlias("name") var name: String,
        @JsonAlias("country") var country: StatsBombCountry)