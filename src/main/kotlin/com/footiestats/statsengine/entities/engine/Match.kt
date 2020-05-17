package com.footiestats.statsengine.entities.engine

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Match(
        var matchDate: LocalDateTime,
        @ManyToOne var competition: Competition,
        @ManyToOne var season: Season,
        @ManyToOne var homeTeam: Team,
        @ManyToOne var awayTeam: Team,
        @ManyToMany var homeTeamManagers: MutableList<Manager>,
        @ManyToMany var awayTeamManagers: MutableList<Manager>,
        var homeScore: Int?,
        var awayScore: Int?,
        var matchStatus: String,
        var lastUpdated: LocalDateTime,
        @ManyToOne var metadata: MatchMetadata,
        var matchWeek: Int,
        @ManyToOne var competitionStage: CompetitionStage,
        @ManyToOne var stadium: Stadium?,
        @ManyToOne var referee: Referee?,
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null)