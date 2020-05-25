package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalTime

class StatsBombEvent(
        @JsonAlias("id") val id: String,
        @JsonAlias("index") val index: Int,
        @JsonAlias("period") val period: Int,
        @JsonAlias("timestamp") val timestamp: String,
        @JsonAlias("minute") val minute: Byte,
        @JsonAlias("second") val second: Byte,
        @JsonAlias("type") val type: StatsBombEventType,
        @JsonAlias("possession") val possession: Int,
        @JsonAlias("possession_team") val possessionTeam: StatsBombTeamSimple,
        @JsonAlias("play_pattern") val playPattern: StatsBombPlayPattern,
        @JsonAlias("team") val team: StatsBombTeam,
        @JsonAlias("duration") val duration: Double,
        @JsonAlias("tactics") val tactics: Iterable<StatsBombTactics>
)