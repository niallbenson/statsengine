package com.footiestats.statsengine.entities.statsbomb

import com.fasterxml.jackson.annotation.JsonAlias

class StatsBombMetadata(
        @JsonAlias("data_version") var dataVersion: String,
        @JsonAlias("shot_fidelity_version") var shotFidelityVersion: String?,
        @JsonAlias("xy_fidelity_version") var xyFidelityVersion: String?)