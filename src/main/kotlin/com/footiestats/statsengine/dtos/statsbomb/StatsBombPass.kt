package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombPass(
        @JsonAlias("recipient") val recipient: StatsBombPlayerSimple?,
        @JsonAlias("length") val length: Double,
        @JsonAlias("angle") val angle: Double,
        @JsonAlias("height") val height: StatsBombHeight,
        @JsonAlias("end_location") val endLocation: Array<Double>,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart?,
        @JsonAlias("type") val type: StatsBombEventType?,
        @JsonAlias("outcome") val outcome: StatsBombOutcome?,
        @JsonAlias("no_touch") val noTouch: Boolean?,
        @JsonAlias("cross") val cross: Boolean?,
        @JsonAlias("switch") val switch: Boolean?,
        @JsonAlias("inswinging") val inswinging: Boolean?,
        @JsonAlias("technique") val technique: StatsBombTechnique?,
        @JsonAlias("assisted_shot_id") val assistedShotId: String?,
        @JsonAlias("shot_assist") val shotAssist: Boolean?,
        @JsonAlias("aerial_won") val aerialWon: Boolean?,
        @JsonAlias("goal_assist") val goalAssist: Boolean?,
        @JsonAlias("outswsinging") val outswinging: Boolean?,
        @JsonAlias("cut_back") val cutBack: Boolean?,
        @JsonAlias("through_ball") val throughBall: Boolean?,
        @JsonAlias("miscommunication") val miscommunication: Boolean?,
        @JsonAlias("deflected") val deflected: Boolean?,
        @JsonAlias("straight") val straight: Boolean?,
        @JsonAlias("backheel") val backheel: Boolean?
)