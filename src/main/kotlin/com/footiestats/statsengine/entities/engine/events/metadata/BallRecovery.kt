package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class BallRecovery(
        @Id @GeneratedValue var id: Long? = null
) {
    var recoveryFailure: Boolean? = null
    var offensive: Boolean? = null
}