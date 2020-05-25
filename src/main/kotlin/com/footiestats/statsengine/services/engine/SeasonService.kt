package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.repos.engine.SeasonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SeasonService(private val seasonRepository: SeasonRepository) {

    fun getSeason(id: Long) = seasonRepository.findByIdOrNull(id)
}