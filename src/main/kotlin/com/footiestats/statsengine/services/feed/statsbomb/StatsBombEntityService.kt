package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.repos.engine.SourceRepository
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.stereotype.Service

@Service
class StatsBombEntityService(private val sourceRepository: SourceRepository) {

    fun getStatsBombSource(): Source {
        val statsBombSource = sourceRepository.findByName("StatsBomb")
                ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

        return statsBombSource
    }
}