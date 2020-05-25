package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.repos.engine.CompetitionSeasonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CompetitionSeasonService(private val competitionSeasonRepository: CompetitionSeasonRepository) {

    fun getAll() = competitionSeasonRepository.findAll()

    fun getOne(id: Long) = competitionSeasonRepository.findByIdOrNull(id)

    fun getBySourceName(name: String) = competitionSeasonRepository.findAllByCompetition_Source_Name(name)
}