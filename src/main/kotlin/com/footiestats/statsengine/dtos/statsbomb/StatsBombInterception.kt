package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombInterception(
        @JsonAlias("outcome") val interception: StatsBombOutcome
)