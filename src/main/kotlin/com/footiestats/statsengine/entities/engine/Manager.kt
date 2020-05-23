package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDate

@NodeEntity
class Manager protected constructor(
        var name: String,
        var nickname: String?,
        var dateOfBirth: LocalDate?,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("BORN_IN")
    lateinit var country: Country

    @Relationship("IMPORTED_FROM")
    lateinit var source: Source

    constructor(name: String, nickname: String?, dateOfBirth: LocalDate?, sourceExternalId: String, country: Country,
                source: Source) : this(name, nickname, dateOfBirth, sourceExternalId) {
        this.country = country
        this.source = source
    }
}