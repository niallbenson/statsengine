package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCompetitionSimple(
        @JsonAlias("competition_id") val competitionId: Long,
        @JsonAlias("country_name") val countryName: String,
        @JsonAlias("competition_name") val competitionName: String)