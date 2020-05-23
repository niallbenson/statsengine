package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class MatchMetadata protected constructor(
        var dataVersion: String,
        var shotFidelityVersion: String?,
        var xyFidelityVersion: String?,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("IMPORTED_FROM")
    lateinit var source: Source

    constructor(dataVersion: String, shotFidelityVersion: String?, xyFidelityVersion: String?, source: Source)
            : this(dataVersion, shotFidelityVersion, xyFidelityVersion) {
        this.source = source
    }
}