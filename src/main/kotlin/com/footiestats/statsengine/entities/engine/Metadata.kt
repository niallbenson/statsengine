package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Metadata(
        var dataVersion: String,
        var shotFidelityVersion: String,
        @Id @GeneratedValue var id: Long? = null)