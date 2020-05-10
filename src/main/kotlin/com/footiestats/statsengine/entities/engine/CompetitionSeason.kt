package com.footiestats.statsengine.entities.engine

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class CompetitionSeason(
        @ManyToOne var competition: Competition,
        @ManyToOne var season: Season,
        @Id @GeneratedValue var id: Long? = null
)