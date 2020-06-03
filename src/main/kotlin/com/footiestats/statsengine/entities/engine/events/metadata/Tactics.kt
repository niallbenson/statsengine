package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.*

@Entity
class Tactics(
        var formation: Int,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany(cascade = [CascadeType.ALL]) var lineup = mutableSetOf<TacticalLineupPlayer>()
}