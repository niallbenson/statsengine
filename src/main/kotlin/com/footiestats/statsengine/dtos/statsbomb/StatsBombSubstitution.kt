package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombSubstitution(
        @JsonAlias("outcome") val outcome: StatsBombOutcome,
        @JsonAlias("replacement") val replacement: StatsBombPlayerSimple
)