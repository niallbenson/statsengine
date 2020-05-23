package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Team protected constructor(
        var name: String,
        var gender: Gender,
        var teamGroup: String?,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null
) {
    @Relationship("LOCATED_IN")
    lateinit var country: Country

    @Relationship("MANAGED_BY")
    var managers: MutableList<Manager>? = null

    @Relationship("IMPORTED_FROM")
    lateinit var source: Source

    constructor(name: String, gender: Gender, teamGroup: String?, sourceExternalId: String, country: Country,
                managers: MutableList<Manager>?, source: Source) : this(name, gender, teamGroup, sourceExternalId) {
        this.country = country
        this.managers = managers
        this.teamGroup = teamGroup
        this.sourceExternalId = sourceExternalId
        this.source = source
    }
}