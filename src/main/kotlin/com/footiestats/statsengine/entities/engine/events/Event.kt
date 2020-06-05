package com.footiestats.statsengine.entities.engine.events

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.engine.Team
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import java.time.LocalTime
import javax.persistence.*

@Entity
class Event(
        @ManyToOne var match: Match,
        var eventIndex: Int,
        var period: Int,
        var timestamp: LocalTime,
        var minute: Int,
        var second: Int,
        @ManyToOne var type: EventType,
        var possession: Int,
        @ManyToOne var possessionTeam: Team,
        @OneToOne var playPattern: PlayPattern,
        @ManyToOne var eventTeam: Team,
        var duration: Double,
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null) {

    @ManyToMany(cascade = [CascadeType.ALL])  var relatedEvents = mutableSetOf<Event>()

    @ManyToOne var player: Player? = null

    @OneToOne(cascade = [CascadeType.ALL]) var tactics: Tactics? = null
    @OneToOne(cascade = [CascadeType.ALL])  var halfStart: HalfStart? = null
    @OneToOne(cascade = [CascadeType.ALL])  var position: Position? = null
    @OneToOne(cascade = [CascadeType.ALL])  var location: Location2D? = null
    @OneToOne(cascade = [CascadeType.ALL])  var pass: Pass? = null
    @OneToOne(cascade = [CascadeType.ALL])  var carry: Carry? = null
    @OneToOne(cascade = [CascadeType.ALL])  var dribble: Dribble? = null
    @OneToOne(cascade = [CascadeType.ALL])  var ballReceipt: BallReceipt? = null
    @OneToOne(cascade = [CascadeType.ALL])  var ballRecovery: BallRecovery? = null
    @OneToOne(cascade = [CascadeType.ALL])  var interception: Interception? = null
    @OneToOne(cascade = [CascadeType.ALL])  var clearance: Clearance? = null
    @OneToOne(cascade = [CascadeType.ALL])  var duel: Duel? = null
    @OneToOne(cascade = [CascadeType.ALL])  var shot: Shot? = null
    @OneToOne(cascade = [CascadeType.ALL])  var goalKeeper: GoalKeeper? = null
    @OneToOne(cascade = [CascadeType.ALL])  var foulCommitted: FoulCommitted? = null
    @OneToOne(cascade = [CascadeType.ALL])  var foulWon: FoulWon? = null
    @OneToOne(cascade = [CascadeType.ALL])  var badBehaviour: BadBehaviour? = null
    @OneToOne(cascade = [CascadeType.ALL])  var substitution: Substitution? = null
    @OneToOne(cascade = [CascadeType.ALL])  var injuryStoppage: InjuryStoppage? = null
    @OneToOne(cascade = [CascadeType.ALL])  var fiftyFifty: FiftyFifty? = null
    @OneToOne(cascade = [CascadeType.ALL])  var block: Block? = null
    @OneToOne(cascade = [CascadeType.ALL])  var miscontrol: Miscontrol? = null
    @OneToOne(cascade = [CascadeType.ALL])  var halfEnd: HalfEnd? = null
    @OneToOne(cascade = [CascadeType.ALL])  var playerOff: PlayerOff? = null

    var underPressure: Boolean? = null
    var counterPress: Boolean? = null
    var out: Boolean? = null
    var offCamera: Boolean? = null
}