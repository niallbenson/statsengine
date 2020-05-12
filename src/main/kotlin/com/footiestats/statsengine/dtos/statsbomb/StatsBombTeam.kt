package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombTeam(
        @JsonAlias("home_team_id", "away_team_id") var teamId: Long,
        @JsonAlias("home_team_name", "away_team_name") var teamName: String,
        @JsonAlias("home_team_gender", "away_team_gender") var teamGender: String,
        @JsonAlias("home_team_group", "away_team_group") var teamGroup: String?,
        @JsonAlias("country") var country: StatsBombCountry,
        @JsonAlias("managers") var managers: ArrayList<StatsBombManager>?)