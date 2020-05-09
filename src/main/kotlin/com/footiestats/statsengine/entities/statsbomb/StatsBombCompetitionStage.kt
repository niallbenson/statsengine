package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCompetitionStage(
        @JsonAlias("id") var id: Long,
        @JsonAlias("name") var name: String)