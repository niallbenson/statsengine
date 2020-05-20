package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombTactics(
    @JsonAlias("formation") val formation: Int,
    @JsonAlias("lineup") val lineup: StatsBombTacticsLineupPlayer
)