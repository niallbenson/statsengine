package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.StringToGenderConverter
import com.footiestats.statsengine.entities.engine.enums.Gender
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.typeconversion.Convert

@NodeEntity
class Competition protected constructor(
        var name: String,
        @Convert(StringToGenderConverter::class) var gender: Gender,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship(type = "OCCURS_IN")
    lateinit var country: Country

    @Relationship(type = "IMPORTED_FROM")
    lateinit var source: Source

    constructor(name: String, gender: Gender, sourceExternalId: String, country: Country, source: Source)
            : this(name, gender, sourceExternalId) {
        this.country = country
        this.source = source
    }
}