package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.SeasonDTO
import com.footiestats.statsengine.entities.engine.Season

class SeasonMapper {
    companion object {
        fun toDto(season: Season): SeasonDTO {
            return SeasonDTO(
                    season.id ?: -1,
                    season.name
            )
        }
    }
}