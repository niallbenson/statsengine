package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import javax.persistence.*

@Entity
class Team(
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        var name: String,
        var gender: Gender,
        var group: String,
        @ManyToOne var country: Country,
        @OneToMany var managers: MutableList<Manager>,
        @Id @GeneratedValue var id: Long? = null)