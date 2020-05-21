package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Country(
        var name: String,
        @Relationship(type = "IMPORTED_FROM") var source: Source,
        var sourceExternalId: String?,
        @Id var id: Long? = null)