package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombBadBehaviour(
        @JsonAlias("card") val card: StatsBombCard
)