package com.footiestats.statsengine.entities.engine

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Match(
        var matchDate: LocalDateTime,
        var homeScore: Int?,
        var awayScore: Int?,
        var matchStatus: String,
        var lastUpdated: LocalDateTime,
        var matchWeek: Int,
        var sourceExternalId: String,
        @ManyToOne var competitionSeason: CompetitionSeason,
        @ManyToOne var homeTeam: Team,
        @ManyToOne var awayTeam: Team,
        @ManyToMany var homeTeamManagers: MutableList<Manager>,
        @ManyToMany var awayTeamManagers: MutableList<Manager>,
        @OneToOne var metadata: MatchMetadata,
        @ManyToOne var competitionStage: CompetitionStage,
        @ManyToOne var stadium: Stadium? = null,
        @ManyToOne var referee: Referee? = null,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match") var lineups = mutableSetOf<MatchLineup>()
}