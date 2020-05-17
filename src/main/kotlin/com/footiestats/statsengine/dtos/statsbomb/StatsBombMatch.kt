package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombMatch(
        @JsonAlias("match_id") var matchId: Long,
        @JsonAlias("match_date") var matchDate: String,
        @JsonAlias("kick_off") var kickOff: String,
        @JsonAlias("competition") var competition: StatsBombCompetitionSimple,
        @JsonAlias("season") var season: StatsBombSeason,
        @JsonAlias("home_team") var homeTeam: StatsBombTeam,
        @JsonAlias("away_team") var awayTeam: StatsBombTeam,
        @JsonAlias("home_score") var homeScore: Int?,
        @JsonAlias("away_score") var awayScore: Int?,
        @JsonAlias("match_status") var matchStatus: String,
        @JsonAlias("last_updated") var lastUpdated: String,
        @JsonAlias("metadata") var metadata: StatsBombMatchMetadata,
        @JsonAlias("match_week") var matchWeek: Int,
        @JsonAlias("competition_stage") var competitionStage: StatsBombCompetitionStage,
        @JsonAlias("stadium") var stadium: StatsBombStadium?,
        @JsonAlias("referee") var referee: StatsBombReferee?)