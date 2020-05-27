package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombSeason(
        @JsonAlias("season_id") val seasonId: Long,
        @JsonAlias("season_name") val seasonName: String)