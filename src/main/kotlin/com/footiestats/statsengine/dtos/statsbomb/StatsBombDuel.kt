package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombDuel(
        @JsonAlias("outcome") val outcome: StatsBombOutcome?,
        @JsonAlias("type") val type: StatsBombDuelType
)