package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombBallRecovery(
        @JsonAlias("recovery_failure") val recoveryFailure: Boolean
)