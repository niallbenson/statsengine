package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Country(
        var name: String,
        @ManyToOne var source: Source,
        var sourceExternalId: String?,
        @Id @GeneratedValue var id: Long? = null)