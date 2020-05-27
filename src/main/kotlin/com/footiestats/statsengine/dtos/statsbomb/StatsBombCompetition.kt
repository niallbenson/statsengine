package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCompetition(
        @JsonAlias("competition_id") val competitionId: Long,
        @JsonAlias("season_id") val seasonId: Long,
        @JsonAlias("country_name") val countryName: String,
        @JsonAlias("competition_name") val competitionName: String,
        @JsonAlias("competition_gender") val competitionGender: String,
        @JsonAlias("season_name") val seasonName: String,
        @JsonAlias("match_updated") val matchUpdated: String,
        @JsonAlias("match_available") val matchAvailable: String)