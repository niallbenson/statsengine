package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.SeasonDTO
import com.footiestats.statsengine.entities.engine.Season
import org.springframework.stereotype.Service

@Service
class SeasonMapper {

    fun toDto(season: Season): SeasonDTO {
        return SeasonDTO(
                season.id ?: -1,
                season.name
        )
    }

}