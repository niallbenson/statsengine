package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombCarry(
        @JsonAlias("end_location") val endLocation: Array<Double>
)