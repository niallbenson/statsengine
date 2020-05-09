package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Season(
        var name: String,
        @Id @GeneratedValue var id: Long? = null)