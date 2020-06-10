package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.CompetitionDTO
import com.footiestats.statsengine.dtos.engine.mappers.CompetitionMapper
import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.repos.engine.CompetitionRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CompetitionService(
        private val competitionRepository: CompetitionRepository,
        private val competitionMapper: CompetitionMapper
) {

    fun getCompetitionDtos(): Array<CompetitionDTO> {
        return competitionRepository.findAll()
                .map { competitionMapper.toDto(it) }
                .toTypedArray()
    }

    fun getCompetitionDto(id: Long): CompetitionDTO {
        val competition = competitionRepository.findByIdOrNull(id)
                ?: throw EntityNotFound("Competition not found for id $id")

        return competitionMapper.toDto(competition)
    }

}