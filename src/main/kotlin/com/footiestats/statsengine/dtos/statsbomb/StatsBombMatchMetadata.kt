package com.footiestats.statsengine.dtos.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombMatchMetadata(
        @JsonAlias("data_version") val dataVersion: String,
        @JsonAlias("shot_fidelity_version") val shotFidelityVersion: String?,
        @JsonAlias("xy_fidelity_version") val xyFidelityVersion: String?)