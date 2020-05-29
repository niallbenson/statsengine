package com.footiestats.statsengine.entities.engine.events.metadata

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PlayPattern(
        var name: String,
        @Id @GeneratedValue var id: Long? = null)