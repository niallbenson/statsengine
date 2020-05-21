package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDateTime

@NodeEntity
class Match(
        var matchDate: LocalDateTime,
        @Relationship("MATCH_OF") var competitionSeason: CompetitionSeason,
        @Relationship("PARTICIPANT") var homeTeam: Team,
        @Relationship("PARTICIPANT") var awayTeam: Team,
        @Relationship("MANAGER") var homeTeamManagers: MutableList<Manager>,
        @Relationship("MANAGER") var awayTeamManagers: MutableList<Manager>,
        var homeScore: Int?,
        var awayScore: Int?,
        var matchStatus: String,
        var lastUpdated: LocalDateTime,
        @Relationship("METADATA") var metadata: MatchMetadata,
        var matchWeek: Int,
        @Relationship("STAGE") var competitionStage: CompetitionStage,
        @Relationship("VENUE")  var stadium: Stadium?,
        @Relationship("REFEREE")  var referee: Referee?,
        @Relationship("IMPORTED_FROM")  var source: Source,
        var sourceExternalId: String,
        @Id var id: Long? = null)