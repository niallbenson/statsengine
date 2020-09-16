package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import javax.persistence.*

@Entity
data class FreezeFrame(
        @ManyToOne(cascade = [CascadeType.ALL]) val location: Location2D,
        @ManyToOne val player: Player,
        @ManyToOne val position: Position,
        val teammate: Boolean,
        @Id @GeneratedValue val id: Long? = null
)
