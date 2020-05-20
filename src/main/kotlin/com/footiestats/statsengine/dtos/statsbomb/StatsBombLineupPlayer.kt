package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombLineupPlayer(
    @JsonAlias("player_id") var playerId: Long,
    @JsonAlias("player_name") var playerName: String,
    @JsonAlias("player_nickname") var playerNickname: String?,
    @JsonAlias("jersey_number") var jerseyNumber: Byte,
    @JsonAlias("country") var country: StatsBombCountry?
)