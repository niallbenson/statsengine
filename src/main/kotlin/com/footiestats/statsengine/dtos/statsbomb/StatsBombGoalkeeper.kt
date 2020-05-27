package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombGoalkeeper(
        @JsonAlias("end_location") val endLocation: Array<Double>?,
        @JsonAlias("type") val type: StatsBombEventType,
        @JsonAlias("position") val position: StatsBombPosition?,
        @JsonAlias("outcome") val outcome: StatsBombOutcome?,
        @JsonAlias("body_part") val bodyPart: StatsBombBodyPart?,
        @JsonAlias("technique") val technique: StatsBombTechnique?,
        @JsonAlias("shot_saved_off_target") val shotSavedOffTarget: Boolean?,
        @JsonAlias("punched_out") val punchedOut: Boolean?,
        @JsonAlias("shot_saved_to_post") val shotSavedToPost: Boolean?,
        @JsonAlias("lost_in_play") val lostInPlay: Boolean?,
        @JsonAlias("success_in_play") val successInPlay: Boolean?,
        @JsonAlias("saved_to_post") val savedToPost: Boolean?
)