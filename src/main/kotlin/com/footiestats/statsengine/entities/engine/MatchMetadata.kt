package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class MatchMetadata(
        var dataVersion: String,
        var shotFidelityVersion: String?,
        var xyFidelityVersion: String?,
        @Relationship("IMPORTED_FROM") var source: Source,
        @Id var id: Long? = null)