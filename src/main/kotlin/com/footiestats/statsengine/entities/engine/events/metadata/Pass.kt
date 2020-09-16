package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.refdata.BodyPart
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import com.footiestats.statsengine.entities.engine.events.refdata.PassType
import com.footiestats.statsengine.entities.engine.events.refdata.Technique
import javax.persistence.*

@Entity
data class Pass(
        var length: Double,
        var angle: Double,
        @ManyToOne var passHeight: PassHeight,
        @ManyToOne(cascade = [CascadeType.ALL])  var endLocation: Location2D,
        @Id @GeneratedValue var id: Long? = null) {

    @ManyToOne var recipient: Player? = null
    @ManyToOne var bodyPart: BodyPart? = null
    @ManyToOne var eventType: PassType? = null
    @ManyToOne var outcome: Outcome? = null
    @ManyToOne var technique: Technique? = null

    @OneToOne(cascade = [CascadeType.ALL]) var assistedShot: Event? = null

    var noTouch: Boolean? = null
    var isCross: Boolean? = null
    var switch: Boolean? = null
    var inswinging: Boolean? = null
    var outswinging: Boolean? = null
    var shotAssist: Boolean? = null
    var aerialWon: Boolean? = null
    var goalAssist: Boolean? = null
    var cutBack: Boolean? = null
    var throughBall: Boolean? = null
    var miscommunication: Boolean? = null
    var deflected: Boolean? = null
    var straight: Boolean? = null
    var backheel: Boolean? = null
}
