package com.footiestats.statsengine.entities.engine

import javax.persistence.*

@Entity
class LineupPlayer(
        @ManyToOne var match: Match,
        @OneToOne var player: Player,
        var jerseyNumber: Int,
        @Id @GeneratedValue var id: Long? = null
)