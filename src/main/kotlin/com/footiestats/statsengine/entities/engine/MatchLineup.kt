package com.footiestats.statsengine.entities.engine;

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class MatchLineup(
        @Relationship("MATCH") val match: Match,
        @Relationship("TEAM") val team: Team,
        @Id val id: Long? = null
)