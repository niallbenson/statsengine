package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Location3D(
        var x: Double,
        var y: Double,
        var z: Double,
        @Id @GeneratedValue var id: Long? = null
)