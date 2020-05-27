package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombTeam(
        @JsonAlias("home_team_id", "away_team_id") val teamId: Long,
        @JsonAlias("home_team_name", "away_team_name") val teamName: String,
        @JsonAlias("home_team_gender", "away_team_gender") val teamGender: String,
        @JsonAlias("home_team_group", "away_team_group") val teamGroup: String?,
        @JsonAlias("country") val country: StatsBombCountry,
        @JsonAlias("managers") val managers: ArrayList<StatsBombManager>?)