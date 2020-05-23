package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class CompetitionSeason protected constructor(
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship(type = "INSTANCE_OF")
    lateinit var competition: Competition

    @Relationship(type = "INSTANCE_OF")
    lateinit var season: Season

    constructor(competition: Competition, season: Season) : this() {
        this.competition = competition
        this.season = season
    }
}