package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import javax.persistence.*

@Entity
data class Substitution(
        @ManyToOne var replacement: Player,
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var outcome: Outcome? = null

    @OneToOne(mappedBy = "substitution") var event: Event? = null

}
