package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.Event
import javax.persistence.*

@Entity
class Tactics(
        var formation: Int,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany(cascade = [CascadeType.ALL]) var lineup = mutableSetOf<TacticalLineupPlayer>()

    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "tactics") lateinit var event: Event
}