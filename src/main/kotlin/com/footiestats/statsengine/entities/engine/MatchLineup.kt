package com.footiestats.statsengine.entities.engine;

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class MatchLineup(
        @ManyToOne var match: Match,
        @ManyToOne var team: Team,
        @Id @GeneratedValue var id: Long? = null)