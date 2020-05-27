package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombLineup(
    @JsonAlias("team_id") val teamId: Long,
    @JsonAlias("team_name") val teamName: String,
    @JsonAlias("lineup") val players: Iterable<StatsBombLineupPlayer>
)