package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.SeasonDTO
import com.footiestats.statsengine.dtos.engine.mappers.SeasonMapper
import com.footiestats.statsengine.repos.engine.SeasonRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityNotFound
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SeasonService(
        private val seasonRepository: SeasonRepository,
        private val seasonMapper: SeasonMapper
) {

    fun getSeason(id: Long) = seasonRepository.findByIdOrNull(id)

    fun getSeasonDto(id: Long): SeasonDTO {
        val season = seasonRepository.findByIdOrNull(id)
                ?: throw EntityNotFound("Season not found for $id")

        return seasonMapper.toDto(season)
    }

}