package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDate

@NodeEntity
class Manager(
        var name: String,
        var nickname: String?,
        var dateOfBirth: LocalDate?,
        @Relationship("BORN_IN") var country: Country,
        @Relationship("IMPORTED_FROM") var source: Source,
        var sourceExternalId: String,
        @Id var id: Long? = null)