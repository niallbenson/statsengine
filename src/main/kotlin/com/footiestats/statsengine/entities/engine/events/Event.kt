package com.footiestats.statsengine.entities.engine.events

import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Event(
        @Id var id: String,
        var index: Int,
        var period: Int,
        var timestamp: LocalTime,
        var minute: Int,
        var second: Int,
        @ManyToOne var type: EventType,
        var possession: Int

)