package com.footiestats.statsengine.entities.engine;

import javax.persistence.*

@Entity
class MatchLineup(
        @OneToOne val match: Match,
        @ManyToOne val team: Team,
        @Id @GeneratedValue val id: Long? = null
)