package com.footiestats.statsengine.entities.engine.events

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class EventType(
        @Id var id: Long,
        var name: String)