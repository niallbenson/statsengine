package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCompetitionSimple(
        @JsonAlias("competition_id") var competitionId: Long,
        @JsonAlias("country_name") var countryName: String,
        @JsonAlias("competition_name") var competitionName: String)