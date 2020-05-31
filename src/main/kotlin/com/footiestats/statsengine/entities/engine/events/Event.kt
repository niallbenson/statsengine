package com.footiestats.statsengine.entities.engine.events

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.engine.Team
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import java.time.LocalTime
import javax.persistence.*

@Entity
class Event(
        @ManyToOne var match: Match,
        var index: Int,
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

    @OneToMany var relatedEvents = mutableSetOf<Event>()

    @ManyToOne var player: Player? = null

    @OneToOne var tactics: Tactics? = null
    @OneToOne var halfStart: HalfStart? = null
    @OneToOne var position: Position? = null
    @OneToOne var location: Location2D? = null
    @OneToOne var pass: Pass? = null
    @OneToOne var carry: Carry? = null
    @OneToOne var dribble: Dribble? = null
    @OneToOne var ballReceipt: BallReceipt? = null
    @OneToOne var ballRecovery: BallRecovery? = null
    @OneToOne var interception: Interception? = null
    @OneToOne var clearance: Clearance? = null
    @OneToOne var duel: Duel? = null
    @OneToOne var shot: Shot? = null
    @OneToOne var goalKeeper: GoalKeeper? = null
    @OneToOne var foulCommitted: FoulCommitted? = null
    @OneToOne var foulWon: FoulWon? = null
    @OneToOne var badBehaviour: BadBehaviour? = null
    @OneToOne var recovery: BallRecovery? = null
    @OneToOne var substitution: Substitution? = null
    @OneToOne var injuryStoppage: InjuryStoppage? = null
    @OneToOne var fiftyFifty: FiftyFifty? = null
    @OneToOne var block: Block? = null
    @OneToOne var miscontrol: Miscontrol? = null
    @OneToOne var halfEnd: HalfEnd? = null
    @OneToOne var playerOff: PlayerOff? = null

    var underPressure: Boolean? = null
    var counterPress: Boolean? = null
    var out: Boolean? = null
    var offCamera: Boolean? = null
}