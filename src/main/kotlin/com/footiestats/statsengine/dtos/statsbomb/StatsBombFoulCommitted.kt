package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombFoulCommitted(
        @JsonAlias("offensive") val offensive: Boolean?,
        @JsonAlias("type") val type: StatsBombFoulCommittedType?,
        @JsonAlias("card") val card: StatsBombCard?,
        @JsonAlias("advantage") val advantage: Boolean?,
        @JsonAlias("penalty") val penalty: Boolean?
)