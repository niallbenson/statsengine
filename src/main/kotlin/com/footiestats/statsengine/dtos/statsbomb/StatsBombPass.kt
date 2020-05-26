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
        @JsonAlias("cross") val cross: Boolean?
)