package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombShot(
        @JsonAlias("statsbomb_xg") val statsbombXg: Double,
        @JsonAlias("end_location") val endLocation: Array<Double>,
        @JsonAlias("key_pass_id") val keyPassId: String?,
        @JsonAlias("technique") val technique: StatsBombTechnique,
        @JsonAlias("outcome") val outcome: StatsBombOutcome,
        @JsonAlias("type") val type: StatsBombEventType,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart,
        @JsonAlias("freeze_frame") val freezeFrame: Iterable<StatsBombFreezeFrame>,
        @JsonAlias("first_time") val firstTime: Boolean?
)