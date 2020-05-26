package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombBallReceipt(
        @JsonAlias("outcome") val outcome: StatsBombOutcome
)