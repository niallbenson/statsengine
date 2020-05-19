package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombLineup(
    @JsonAlias("team_id") var teamId: Long,
    @JsonAlias("team_name") var teamName: String,
    @JsonAlias("lineup") var players: Iterable<StatsBombLineupPlayer>
)