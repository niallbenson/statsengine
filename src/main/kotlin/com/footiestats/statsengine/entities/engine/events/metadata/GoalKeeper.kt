package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.EventType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class GoalKeeper(
    @ManyToOne var eventType: EventType,
    @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var endLocation: Location2D? = null
    @ManyToOne var position: Position? = null
    @ManyToOne var outcome: Outcome? = null
    @ManyToOne var bodyPart: BodyPart? = null
    @ManyToOne var technique: Technique? = null

    var shotSavedOffTarget: Boolean? = null
    var puchedOut: Boolean? = null
    var shotSavedToPost: Boolean? = null
    var lostInPlay: Boolean? = null
    var successInPlay: Boolean? = null
    var savedToPost: Boolean? = null
    var lostOut: Boolean? = null
    var successOut: Boolean? = null
    var penaltySavedToPost: Boolean? = null
}