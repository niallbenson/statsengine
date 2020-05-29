package com.footiestats.statsengine.entities.engine.events

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class EventType(
        var name: String,
        @Id @GeneratedValue var id: Long? = null)