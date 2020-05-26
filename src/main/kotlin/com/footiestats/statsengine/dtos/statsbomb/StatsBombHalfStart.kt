package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombHalfStart(
        @JsonAlias("late_video_start") val lateVideoStart: Boolean
)