package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.Card
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class BadBehaviour(
        @ManyToOne var card: Card,
        @Id @GeneratedValue var id: Long? = null
)
