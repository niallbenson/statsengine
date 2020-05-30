package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FreezeFrame(
        @ManyToOne val location: Location2D,
        @ManyToOne val player: Player,
        @ManyToOne val position: Position,
        val teammate: Boolean,
        @Id @GeneratedValue val id: Long? = null
)