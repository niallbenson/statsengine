package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.*
import javax.persistence.*

@Entity
data class GoalKeeper(
        @ManyToOne var goalkeeperType: GoalkeeperType,
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne(cascade = [CascadeType.ALL]) var endLocation: Location2D? = null
    @ManyToOne var position: Position? = null
    @ManyToOne var outcome: Outcome? = null
    @ManyToOne var bodyPart: BodyPart? = null
    @ManyToOne var technique: Technique? = null

    var shotSavedOffTarget: Boolean? = null
    var punchedOut: Boolean? = null
    var shotSavedToPost: Boolean? = null
    var lostInPlay: Boolean? = null
    var successInPlay: Boolean? = null
    var savedToPost: Boolean? = null
    var lostOut: Boolean? = null
    var successOut: Boolean? = null
    var penaltySavedToPost: Boolean? = null
}
