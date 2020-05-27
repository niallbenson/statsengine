package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombFreezeFrame(
        @JsonAlias("location") val location: Array<Double>,
        @JsonAlias("player") val player: StatsBombPlayerSimple,
        @JsonAlias("position") val position: StatsBombPosition,
        @JsonAlias("teammate") val teammate: Boolean
)