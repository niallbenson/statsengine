package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Substitution(
        @ManyToOne var replacement: Player,
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var outcome: Outcome? = null
}