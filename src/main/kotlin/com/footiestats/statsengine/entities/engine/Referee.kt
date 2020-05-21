package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Referee(
        var name: String,
        @Relationship("BORN_IN") var country: Country?,
        @Relationship("IMPORTED_FROM") var source: Source,
        var sourceExternalId: String,
        @Id var id: Long? = null)