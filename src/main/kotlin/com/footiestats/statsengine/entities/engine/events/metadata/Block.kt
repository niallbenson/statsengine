package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Block(
        @Id @GeneratedValue var id: Long? = null
) {
    var deflection: Boolean? = null
    var saveBlock: Boolean? = null
    var offensive: Boolean? = null
}
