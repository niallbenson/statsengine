package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import javax.persistence.*

@Entity
class Team(
        var name: String,
        var gender: Gender,
        var teamGroup: String?,
        @ManyToOne var country: Country,
        @ManyToMany var managers: MutableList<Manager>,
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null)