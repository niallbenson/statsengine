package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.repos.engine.SourceRepository
import org.springframework.stereotype.Service

@Service
class SourceService(private val sourceRepository: SourceRepository) {

    fun seed() {
        if (sourceRepository.findAll().iterator().hasNext()) return

        val statsBombSource = Source("StatsBomb")
        sourceRepository.save(statsBombSource)
    }
}