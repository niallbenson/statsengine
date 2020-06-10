package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.CompetitionDTO
import com.footiestats.statsengine.entities.engine.Competition
import org.springframework.stereotype.Service

@Service
class CompetitionMapper(private val seasonMapper: SeasonMapper) {

    fun toDto(competition: Competition): CompetitionDTO {
        return CompetitionDTO(
                competition.id ?: -1,
                competition.name,
                competition.gender.name,
                competition.country.name,
                competition.country.wikiFlag ?: "",
                competition.competitionSeasons
                        .map { seasonMapper.toDto(it.season) }
                        .sortedByDescending { it.name }
                        .toTypedArray()
        )
    }
}