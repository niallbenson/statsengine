package com.footiestats.statsengine.entities.engine

import javax.persistence.*

@Entity
class Country(
        var name: String,
        var sourceExternalId: String?,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null
) {
    var wikiFlag: String? = null
}