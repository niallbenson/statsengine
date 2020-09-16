package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Miscontrol(
        @Id @GeneratedValue var id: Long? = null
) {
    var aerialWon: Boolean? = null
}
