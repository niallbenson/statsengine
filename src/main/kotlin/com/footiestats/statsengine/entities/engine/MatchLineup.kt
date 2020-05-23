package com.footiestats.statsengine.entities.engine;

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class MatchLineup protected constructor(
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("MATCH")
    lateinit var match: Match

    @Relationship("TEAM")
    lateinit var team: Team

    constructor(match: Match, team: Team) : this() {
        this.match = match
        this.team = team
    }
}