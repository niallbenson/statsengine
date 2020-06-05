package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class MatchMetadata(
        var dataVersion: String?,
        var shotFidelityVersion: String?,
        var xyFidelityVersion: String?,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null)