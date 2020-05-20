package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombTeamSimple(
        @JsonAlias("id") var id: Long,
        @JsonAlias("name") var name: String)