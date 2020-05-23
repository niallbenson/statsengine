package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Referee protected constructor(
        var name: String,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null) {

    @Relationship("BORN_IN")
    var country: Country? = null

    @Relationship("IMPORTED_FROM")
    lateinit var source: Source

    constructor(name: String, sourceExternalId: String, country: Country?, source: Source)
            : this(name, sourceExternalId) {
        this.source = source
    }
}