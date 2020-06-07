package com.footiestats.statsengine.dtos.engine

import java.time.LocalDateTime

data class MatchDTO(
        val id: Long,
        val homeTeamName: String,
        val awayTeamName: String,
        val homeTeamId: Long,
        val awayTeamId: Long,
        val matchDate: LocalDateTime,
        val status: String,
        val homeScore: Int?,
        val awayScore: Int?,
        val competitionStage: String)