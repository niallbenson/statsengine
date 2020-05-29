package com.footiestats.statsengine.entities.engine.events

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.engine.Team
import com.footiestats.statsengine.entities.engine.events.metadata.*
import java.awt.geom.Point2D
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
        var externalSourceId: String,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany var relatedEvents = mutableListOf<Event>()

    @ManyToOne var player: Player? = null

    @OneToOne var tactics: Tactics? = null
    @OneToOne var halfStart: HalfStart? = null
    @OneToOne var position: Position? = null
    @OneToOne var location: Location2D? = null
    @OneToOne var pass: Pass? = null
    @OneToOne var carry: Carry? = null
    @OneToOne var dribble: Dribble? = null
    @OneToOne var ballReceipt: BallReceipt? = null
    @OneToOne var interception: Interception? = null
    @OneToOne var clearance: Clearance? = null
    @OneToOne var duel: Duel? = null
}