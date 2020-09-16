package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Interception(
        @ManyToOne var outcome: Outcome,
        @Id @GeneratedValue var id: Long? = null
)
