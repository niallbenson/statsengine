package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.*

@Entity
class Carry(
        @ManyToOne(cascade = [CascadeType.ALL]) var endLocation: Location2D,
        @Id @GeneratedValue var id: Long? = null
)