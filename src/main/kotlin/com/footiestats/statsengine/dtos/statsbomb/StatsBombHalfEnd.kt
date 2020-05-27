package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombHalfEnd(
        @JsonAlias("early_video_end") val earlyVideoEnd: Boolean?
)