package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.EventType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Duel(
        @ManyToOne var eventType: EventType,
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var outcome: Outcome? = null
}