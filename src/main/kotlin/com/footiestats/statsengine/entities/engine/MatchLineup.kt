package com.footiestats.statsengine.entities.engine

import javax.persistence.*

@Entity
data class MatchLineup(
        @ManyToOne var match: Match,
        @ManyToOne var team: Team,
        @Id @GeneratedValue var id: Long? = null
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchLineup") var players = mutableSetOf<LineupPlayer>()
}
