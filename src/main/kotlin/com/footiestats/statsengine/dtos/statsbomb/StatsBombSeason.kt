package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombSeason(
        @JsonAlias("season_id") var seasonId: Long,
        @JsonAlias("season_name") var seasonName: String)