package com.footiestats.statsengine.entities.engine

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Match(
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        var matchDate: Date,
        @ManyToOne var competition: Competition,
        @ManyToOne var season: Season,
        @ManyToOne var homeTeam: Team,
        @ManyToOne var awayTeam: Team,
        var homeScore: Int?,
        var awayScore: Int?,
        var matchStatud: String,
        var lastUpdated: Date,
        @ManyToOne var metadata: Metadata,
        var matchWek: Int,
        @ManyToOne var competitionStage: CompetitionStage,
        @ManyToOne var stadium: Stadium,
        @ManyToOne var referee: Referee,
        @Id @GeneratedValue var id: Long? = null)