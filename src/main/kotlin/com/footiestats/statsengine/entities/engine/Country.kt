package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Country protected constructor(
        var name: String,
        var sourceExternalId: String?,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship(type = "IMPORTED_FROM")
    lateinit var source: Source

    constructor(name: String, sourceExternalId: String?, source: Source) : this(name, sourceExternalId) {
        this.source = source
    }
}