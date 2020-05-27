package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombMatch(
        @JsonAlias("match_id") val matchId: Long,
        @JsonAlias("match_date") val matchDate: String,
        @JsonAlias("kick_off") val kickOff: String,
        @JsonAlias("competition") val competition: StatsBombCompetitionSimple,
        @JsonAlias("season") val season: StatsBombSeason,
        @JsonAlias("home_team") val homeTeam: StatsBombTeam,
        @JsonAlias("away_team") val awayTeam: StatsBombTeam,
        @JsonAlias("home_score") val homeScore: Int?,
        @JsonAlias("away_score") val awayScore: Int?,
        @JsonAlias("match_status") val matchStatus: String,
        @JsonAlias("last_updated") val lastUpdated: String,
        @JsonAlias("metadata") val metadata: StatsBombMatchMetadata,
        @JsonAlias("match_week") val matchWeek: Int,
        @JsonAlias("competition_stage") val competitionStage: StatsBombCompetitionStage,
        @JsonAlias("stadium") val stadium: StatsBombStadium?,
        @JsonAlias("referee") val referee: StatsBombReferee?)