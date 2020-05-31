package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.Card
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FoulCommitted(
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var eventType: EventType? = null
    @ManyToOne var card: Card? = null

    var offensive: Boolean? = null
    var advantage: Boolean? = null
    var penalty: Boolean? = null
}