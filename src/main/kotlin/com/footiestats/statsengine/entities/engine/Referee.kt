package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Referee(
    var name: String,
    @ManyToOne var country: Country,
    @Id @GeneratedValue var id: Long? = null)