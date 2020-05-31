package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.BodyPart
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Clearance(
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var bodyPart: BodyPart? = null

    var leftFoot: Boolean? = null
    var rightFoot: Boolean? = null
    var head: Boolean? = null
    var other: Boolean? = null
    var aerialWon: Boolean? = null
}