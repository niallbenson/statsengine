package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.*

@Entity
class Tactics(
        var formation: Int,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany var lineup = mutableListOf<TacticalLineupPlayer>()
}