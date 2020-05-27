package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombFoulCommitted(
        @JsonAlias("offensive") val offensive: Boolean
)