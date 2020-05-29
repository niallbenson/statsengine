package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Carry(
        @ManyToOne var endLocation: Location2D,
        @Id @GeneratedValue var id: Long? = null
)