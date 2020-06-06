package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.MatchMapper
import com.footiestats.statsengine.entities.engine.Season
import com.footiestats.statsengine.repos.engine.MatchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun getMatch(id: Long) = matchRepository.findByIdOrNull(id)

    fun getMatches(competitionId: Long, seasonId: Long) =
            matchRepository.findAllByCompetitionAndSeason(competitionId, seasonId)
                    .map { MatchMapper.toDto(it) }
                    .toTypedArray()
}