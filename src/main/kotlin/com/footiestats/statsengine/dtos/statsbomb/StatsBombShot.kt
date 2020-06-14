package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombShot(
        @JsonAlias("statsbomb_xg") val statsbombXg: Double,
        @JsonAlias("end_location") val endLocation: Array<Double>,
        @JsonAlias("key_pass_id") val keyPassId: String?,
        @JsonAlias("technique") val technique: StatsBombTechnique,
        @JsonAlias("outcome") val outcome: StatsBombOutcome,
        @JsonAlias("type") val type: StatsBombShotType,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart,
        @JsonAlias("freeze_frame") val freezeFrame: Iterable<StatsBombFreezeFrame>?,
        @JsonAlias("first_time") val firstTime: Boolean?,
        @JsonAlias("open_goal") val openGoal: Boolean?,
        @JsonAlias("one_on_one") val oneOnOne: Boolean?,
        @JsonAlias("aerial_won") val aerialWon: Boolean?,
        @JsonAlias("deflected") val deflected: Boolean?,
        @JsonAlias("follows_dribble") val followsDribble: Boolean?,
        @JsonAlias("saved_off_target") val savedOffTarget: Boolean?,
        @JsonAlias("saved_to_post") val savedToPost: Boolean?,
        @JsonAlias("redirect") val redirect: Boolean?,
        @JsonAlias("kick_off") val kickOff: Boolean?
)