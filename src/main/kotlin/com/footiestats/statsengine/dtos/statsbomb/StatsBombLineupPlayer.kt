package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombLineupPlayer(
    @JsonAlias("player_id") val playerId: Long,
    @JsonAlias("player_name") val playerName: String,
    @JsonAlias("player_nickname") val playerNickname: String?,
    @JsonAlias("jersey_number") val jerseyNumber: Int,
    @JsonAlias("country") val country: StatsBombCountry?
)