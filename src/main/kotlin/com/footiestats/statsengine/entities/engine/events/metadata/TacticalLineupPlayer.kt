package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class TacticalLineupPlayer(
        var jerseyNumber: Int,
        @ManyToOne var player: Player,
        @ManyToOne var position: Position,
        @Id @GeneratedValue var id: Long? = null)