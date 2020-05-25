package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.repos.engine.CompetitionRepository
import org.springframework.stereotype.Service

@Service
class CompetitionService(private val competitionRepository: CompetitionRepository) {

    fun getCompetition(id: Long): Competition {
        val c = competitionRepository.findById(id)

        return c.get()
    }

}