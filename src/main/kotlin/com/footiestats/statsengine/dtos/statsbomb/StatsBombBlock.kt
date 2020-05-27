package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombBlock(
        @JsonAlias("deflection") val deflection: Boolean?,
        @JsonAlias("save_block") val saveBock: Boolean?,
        @JsonAlias("offensive") val offensive: Boolean?
)