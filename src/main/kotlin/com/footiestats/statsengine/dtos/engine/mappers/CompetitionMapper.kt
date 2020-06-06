package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.CompetitionDTO
import com.footiestats.statsengine.entities.engine.Competition

class CompetitionMapper{
    companion object {
        fun toDto(competition: Competition): CompetitionDTO {
            return CompetitionDTO(
                    competition.id ?: -1,
                    competition.name,
                    competition.gender.name,
                    competition.country.name,
                    competition.country.wikiFlag ?: "",
                    competition.competitionSeasons.map { SeasonMapper.toDto(it.season) }.toTypedArray()
            )
        }
    }
}