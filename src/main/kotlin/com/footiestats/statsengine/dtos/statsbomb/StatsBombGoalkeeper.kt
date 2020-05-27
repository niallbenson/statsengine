package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombGoalkeeper(
        @JsonAlias("end_location") val endLocation: Array<Double>?,
        @JsonAlias("type") val type: StatsBombEventType,
        @JsonAlias("position") val position: StatsBombPosition?,
        @JsonAlias("outcome") val outcome: StatsBombOutcome?,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart?,
        @JsonAlias("technique") val technique: StatsBombTechnique?
)