package com.footiestats.statsengine.entities.engine

import com.footiestats.statsengine.entities.engine.enums.Gender
import javax.persistence.*

@Entity
data class Competition(
        var name: String,
        var gender: Gender,
        var sourceExternalId: String,
        @ManyToOne var country: Country,
        @ManyToOne var source: Source,
        @Id @GeneratedValue var id: Long? = null) {

    @OneToMany(mappedBy = "competition") var competitionSeasons = mutableSetOf<CompetitionSeason>()
}
