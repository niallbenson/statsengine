package com.footiestats.statsengine.entities.engine.events.metadata

import com.footiestats.statsengine.entities.engine.events.refdata.Card
import com.footiestats.statsengine.entities.engine.events.refdata.FoulCommittedType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class FoulCommitted(
        @Id @GeneratedValue var id: Long? = null
) {
    @ManyToOne var foulCommittedType: FoulCommittedType? = null
    @ManyToOne var card: Card? = null

    var offensive: Boolean? = null
    var advantage: Boolean? = null
    var penalty: Boolean? = null
}