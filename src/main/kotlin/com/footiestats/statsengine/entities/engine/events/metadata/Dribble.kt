package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Dribble(
        @ManyToOne var outcome: Outcome,
        var overrun: Boolean,
        @Id @GeneratedValue var id: Long? = null
) {
    var nutmeg: Boolean? = null
    var noTouch: Boolean? = null
}
