package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Referee(
        var name: String,
        var sourceExternalId: String,
        @ManyToOne var country: Country? = null,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null)
