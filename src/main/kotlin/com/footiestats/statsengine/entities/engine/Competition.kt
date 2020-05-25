package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Competition(
        var name: String,
        var gender: Gender,
        var sourceExternalId: String,
        @ManyToOne var country: Country,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null)