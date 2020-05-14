package com.footiestats.statsengine.entities.engine

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Manager(
        var name: String,
        var nickname: String?,
        var dateOfBirth: LocalDate?,
        @ManyToOne var country: Country,
        @ManyToOne var source: Source,
        var sourceExternalId: String,
        @Id @GeneratedValue var id: Long? = null)