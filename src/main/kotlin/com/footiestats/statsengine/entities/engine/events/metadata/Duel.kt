package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.DuelType
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Duel(
        @ManyToOne var duelType: DuelType,
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var outcome: Outcome? = null
}
