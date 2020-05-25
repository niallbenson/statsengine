package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Stadium(
        var name: String,
        var sourceExternalId: String,
        @ManyToOne var country: Country,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null)