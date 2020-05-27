package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombMiscontrol(
        @JsonAlias("aerial_won") val aerialWon: Boolean
)