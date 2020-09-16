package com.footiestats.statsengine.entities.engine

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Manager(
        var name: String,
        var nickname: String?,
        var dateOfBirth: LocalDate?,
        var sourceExternalId: String,
        @ManyToOne var country: Country,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null)
