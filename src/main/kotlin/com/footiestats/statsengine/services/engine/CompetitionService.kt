package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.CompetitionDTO
import com.footiestats.statsengine.dtos.engine.mappers.CompetitionMapper
import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.repos.engine.CompetitionRepository
import org.springframework.stereotype.Service

@Service
class CompetitionService(private val competitionRepository: CompetitionRepository) {

    fun getCompetitionDtos(): Array<CompetitionDTO> {
        return competitionRepository.findAll()
                .map {CompetitionMapper.toDto(it) }
                .toTypedArray()
    }

    fun getCompetition(id: Long): Competition {
        val c = competitionRepository.findById(id)

        return c.get()
    }

}