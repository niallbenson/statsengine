package com.footiestats.statsengine.entities.engine

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Manager(
        var name: String,
        var nickname: String,
        var dateOfBirth: Date,
        @ManyToOne var country: Country,
        @Id @GeneratedValue var id: Long? = null)