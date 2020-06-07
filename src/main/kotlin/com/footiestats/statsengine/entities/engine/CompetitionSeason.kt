package com.footiestats.statsengine.entities.engine

import javax.persistence.*

@Entity
class CompetitionSeason(
        @ManyToOne var competition: Competition,
        @ManyToOne var season: Season,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany(mappedBy = "competitionSeason", fetch = FetchType.LAZY) var matches = mutableSetOf<Match>()
}