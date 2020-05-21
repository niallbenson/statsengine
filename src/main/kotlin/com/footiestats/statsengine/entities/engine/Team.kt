package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Team(
        var name: String,
        var gender: Gender,
        var teamGroup: String?,
        @Relationship("LOCATED_IN") var country: Country,
        @Relationship("MANAGED_BY") var managers: MutableList<Manager>,
        @Relationship("IMPORTED_FROM") var source: Source,
        var sourceExternalId: String,
        @Id var id: Long? = null)