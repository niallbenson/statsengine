package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Competition(
        @Relationship(type = "OCCURS_IN") var country: Country,
        var name: String,
        var gender: Gender,
        @Relationship(type = "IMPORTED_FROM") var source: Source,
        var sourceExternalId: String,
        @Id var id: Long? = null)