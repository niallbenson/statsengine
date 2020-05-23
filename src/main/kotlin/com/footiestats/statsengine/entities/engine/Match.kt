package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDateTime

@NodeEntity
class Match protected constructor(
        var matchDate: LocalDateTime,
        var homeScore: Int?,
        var awayScore: Int?,
        var matchStatus: String,
        var lastUpdated: LocalDateTime,
        var matchWeek: Int,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("MATCH_OF")
    lateinit var competitionSeason: CompetitionSeason

    @Relationship("PARTICIPANT")
    lateinit var homeTeam: Team

    @Relationship("PARTICIPANT")
    lateinit var awayTeam: Team

    @Relationship("MANAGER")
    lateinit var homeTeamManagers: MutableList<Manager>

    @Relationship("MANAGER")
    lateinit var awayTeamManagers: MutableList<Manager>

    @Relationship("METADATA")
    lateinit var metadata: MatchMetadata

    @Relationship("STAGE")
    lateinit var competitionStage: CompetitionStage

    @Relationship("VENUE")
    var stadium: Stadium? = null

    @Relationship("REFEREE")
    var referee: Referee? = null

    @Relationship("IMPORTED_FROM")
    lateinit var source: Source

    constructor(matchDate: LocalDateTime, homeScore: Int?, awayScore: Int?, matchStatus: String,
                lastUpdated: LocalDateTime, matchWeek: Int, sourceExternalId: String,
                competitionSeason: CompetitionSeason, homeTeam: Team, awayTeam: Team,
                homeTeamManagers: MutableList<Manager>, awayTeamManagers: MutableList<Manager>,
                metadata: MatchMetadata, competitionStage: CompetitionStage, stadium: Stadium?,
                referee: Referee?, source: Source)
            : this(matchDate, homeScore, awayScore, matchStatus, lastUpdated, matchWeek, sourceExternalId) {
        this.competitionSeason = competitionSeason
        this.homeTeam = homeTeam
        this.awayTeam = awayTeam
        this.homeTeamManagers = homeTeamManagers
        this.awayTeamManagers = awayTeamManagers
        this.metadata = metadata
        this.competitionStage = competitionStage
        this.stadium = stadium
        this.referee = referee
        this.source = source
    }
}