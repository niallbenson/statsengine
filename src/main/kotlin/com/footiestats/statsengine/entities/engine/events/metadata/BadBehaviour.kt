package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class BadBehaviour(
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var card: Card? = null
}