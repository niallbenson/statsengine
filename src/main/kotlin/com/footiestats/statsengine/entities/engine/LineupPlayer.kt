package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class LineupPlayer(
        var jerseyNumber: Int,
        @ManyToOne var player: Player,
        @ManyToOne var matchLineup: MatchLineup,
        @Id @GeneratedValue var id: Long? = null)