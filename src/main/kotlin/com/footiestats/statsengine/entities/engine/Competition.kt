package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Competition(
        @ManyToOne var season: Season,
        @ManyToOne var country: Country,
        var name: String,
        var gender: Gender,
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null)