package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombPlayerOff(
        @JsonAlias("permanent") val permanent: Boolean
)