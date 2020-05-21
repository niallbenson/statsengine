package com.footiestats.statsengine.entities.engine

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
class Source(
    var name: String,
    @Id var id: Long? = null)