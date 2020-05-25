package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.repos.engine.MatchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun getMatch(id: Long) = matchRepository.findByIdOrNull(id)

//    fun getByCompetitionSeasonId(id: Long) = matchRepository.findAllByCompetitionSeason(id)
}