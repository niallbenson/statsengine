package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombFoulWon(
        @JsonAlias("defensive") val defensive: Boolean
)