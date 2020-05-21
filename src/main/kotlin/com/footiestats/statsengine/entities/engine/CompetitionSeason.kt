package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class CompetitionSeason(
        @Relationship(type = "INSTANCE_OF")
        var competition: Competition,
        @Relationship(type = "INSTANCE_OF")
        var season: Season,
        @Id var id: Long? = null
)