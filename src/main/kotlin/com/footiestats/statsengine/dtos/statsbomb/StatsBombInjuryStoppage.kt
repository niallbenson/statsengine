package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombInjuryStoppage(
        @JsonAlias("in_chain") val inChain: Boolean
)