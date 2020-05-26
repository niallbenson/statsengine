package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombClearance(
        @JsonAlias("left_foot") val leftFoot: Boolean?,
        @JsonAlias("right_foot") val rightFoot: Boolean?,
        @JsonAlias("other") val other: Boolean?,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart
)