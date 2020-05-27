package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombFiftyFifty(
        @JsonAlias("outcome") val outcome: StatsBombOutcome
)