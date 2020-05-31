package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
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
    @OneToMany var freezeFrame = mutableSetOf<FreezeFrame>()

    var firstTime: Boolean? = null
    var openGoal: Boolean? = null
    var oneOnOne: Boolean? = null
    var aerialWon: Boolean? = null
    var deflected: Boolean? = null
    var followsDribble: Boolean? = null
    var savedOffTarget: Boolean? = null
    var savedToPost: Boolean? = null
    var redirect: Boolean? = null
    var kickOff: Boolean? = null
}