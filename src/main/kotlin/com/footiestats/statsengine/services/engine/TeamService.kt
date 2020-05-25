package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.repos.engine.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TeamService(private val teamRepository: TeamRepository) {

    fun getTeam(id: Long) = teamRepository.findByIdOrNull(id)
}