package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombTacticsLineupPlayer(
        @JsonAlias("player") val playerSimple: StatsBombPlayerSimple,
        @JsonAlias("position") val position: StatsBombPosition,
        @JsonAlias("jersey_number") val jerseyNumber: Byte)