package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias
import com.sun.org.apache.xpath.internal.operations.Bool
import java.time.LocalTime

class StatsBombEvent(
        @JsonAlias("id") val id: String,
        @JsonAlias("index") val index: Int,
        @JsonAlias("period") val period: Int,
        @JsonAlias("timestamp") val timestamp: String,
        @JsonAlias("minute") val minute: Byte,
        @JsonAlias("second") val second: Byte,
        @JsonAlias("type") val type: StatsBombEventType,
        @JsonAlias("possession") val possession: Int,
        @JsonAlias("possession_team") val possessionTeam: StatsBombTeamSimple,
        @JsonAlias("play_pattern") val playPattern: StatsBombPlayPattern,
        @JsonAlias("team") val team: StatsBombTeamSimple,
        @JsonAlias("duration") val duration: Double,
        @JsonAlias("tactics") val tactics: StatsBombTactics?,
        @JsonAlias("related_events") val relatedEvents: Iterable<String>?,
        @JsonAlias("half_start") val halfStart: StatsBombHalfStart?,
        @JsonAlias("player") val player: StatsBombPlayerSimple?,
        @JsonAlias("position") val position: StatsBombPosition?,
        @JsonAlias("location") val location: Array<Double>?,
        @JsonAlias("pass") val pass: StatsBombPass?,
        @JsonAlias("carry") val carry: StatsBombCarry?,
        @JsonAlias("under_pressure") val underPressure: Boolean?,
        @JsonAlias("counterpress") val counterPress: Boolean?,
        @JsonAlias("dribble") val dribble: StatsBombDribble?,
        @JsonAlias("ball_receipt") val ballReceipt: StatsBombBallReceipt?,
        @JsonAlias("interception") val interception: StatsBombInterception?,
        @JsonAlias("clearance") val clearance: StatsBombClearance?,
        @JsonAlias("duel") val duel: StatsBombDuel?,
        @JsonAlias("out") val out: Boolean?,
        @JsonAlias("shot") val shot: StatsBombShot?,
        @JsonAlias("goalkeeper") val goalkeeper: StatsBombGoalkeeper?,
        @JsonAlias("foul_committed") val foulCommitted: StatsBombFoulCommitted?,
        @JsonAlias("foul_won") val foulWon: StatsBombFoulWon?,
        @JsonAlias("bad_behaviour") val badBehaviour: StatsBombBadBehaviour?,
        @JsonAlias("ball_recovery") val ballRecovery: StatsBombBallRecovery?,
        @JsonAlias("substitution") val substitution: StatsBombSubstitution?,
        @JsonAlias("injury_stoppage") val injuryStoppage: StatsBombInjuryStoppage?,
        @JsonAlias("50_50") val fiftyFifty: StatsBombFiftyFifty?,
        @JsonAlias("block") val deflection: StatsBombBlock?,
        @JsonAlias("miscontrol") val miscontrol: StatsBombMiscontrol?,
        @JsonAlias("off_camera") val offCamera: Boolean?,
        @JsonAlias("half_end") val halfEnd: StatsBombHalfEnd?
)