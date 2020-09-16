package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Location2D(
        var x: Double,
        var y: Double,
        @Id @GeneratedValue var id: Long? = null
)
