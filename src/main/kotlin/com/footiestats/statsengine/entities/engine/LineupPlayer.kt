package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class LineupPlayer(
        @Relationship("LINEUP_PLAYER") var player: Player,
        @Relationship("PART_OF_LINEUP") var matchLineup: MatchLineup,
        var jerseyNumber: Byte,
        @Id var id: Long? = null
)