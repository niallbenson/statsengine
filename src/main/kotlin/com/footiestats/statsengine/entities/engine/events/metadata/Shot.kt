package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.BodyPart
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Shot(
        var statsBombXg: Double,
        @OneToOne(cascade = [CascadeType.ALL]) var endLocation: Location3D,
        @ManyToOne var technique: Technique,
        @ManyToOne var outcome: Outcome,
        @ManyToOne var eventType: EventType,
        @ManyToOne var bodyPart: BodyPart,
        @Id @GeneratedValue var id: Long? = null
) {
    @OneToMany(cascade = [CascadeType.ALL]) var freezeFrame = mutableSetOf<FreezeFrame>()

    @OneToOne(cascade = [CascadeType.ALL]) var keyPass: Event? = null

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