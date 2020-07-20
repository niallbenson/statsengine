package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.Location2DDTO
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import org.springframework.stereotype.Service

@Service
class Location2DMapper {

    fun toDto(location2D: Location2D): Location2DDTO {
        return Location2DDTO(
                location2D.x,
                location2D.y
        )
    }
}
