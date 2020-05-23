package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class LineupPlayer protected constructor(
        var jerseyNumber: Byte,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("LINEUP_PLAYER")
    lateinit var player: Player

    @Relationship("PART_OF_LINEUP")
    lateinit var matchLineup: MatchLineup

    constructor(jerseyNumber: Byte, player: Player, matchLineup: MatchLineup) : this(jerseyNumber) {
        this.player = player
        this.matchLineup = matchLineup
    }
}