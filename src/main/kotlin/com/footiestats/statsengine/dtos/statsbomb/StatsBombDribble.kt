package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombDribble(
        @JsonAlias("outcome") val outcome: StatsBombOutcome,
        @JsonAlias("overrun") val overrun: Boolean,
        @JsonAlias("nutmeg") val nutmeg: Boolean?
)