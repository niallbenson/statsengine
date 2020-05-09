package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias
import javax.persistence.*

@Entity
class StatsBombCompetition(
        @JsonAlias("competition_id") var competitionId: Long,
        @JsonAlias("season_id") var seasonId: Long,
        @JsonAlias("country_name") var countryName: String,
        @JsonAlias("competition_name") var competitionName: String,
        @JsonAlias("competition_gender") var competitionGender: String,
        @JsonAlias("season_name") var seasonName: String,
        @JsonAlias("match_updated") var matchUpdated: String,
        @JsonAlias("match_available") var matchAvailable: String,
        @Id @GeneratedValue var id: Long? = null)