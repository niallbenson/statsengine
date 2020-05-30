package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class FoulWon(
        @Id @GeneratedValue var id: Long? = null
) {
    var defensive: Boolean? = null
    var advantage: Boolean? = null
    var penalty: Boolean? = null
}