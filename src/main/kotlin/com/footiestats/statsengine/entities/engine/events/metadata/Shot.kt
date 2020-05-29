package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import javax.persistence.*

@Entity
class Shot(
        var statsBombXg: Double,
        @OneToOne var endLocation: Location2D,
        @OneToOne var keyPass: Event,
        @ManyToOne var technique: Technique,
        @ManyToOne var outcome: Outcome,
        @ManyToOne var eventType: EventType,
        @ManyToOne var bodyPart: BodyPart,
        @Id @GeneratedValue var id: Long? = null
) {

}