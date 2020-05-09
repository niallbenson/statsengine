package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RestResource

interface SourceRepository : PagingAndSortingRepository<Source, Long> {
    fun findByName(name: String): Source?
}

interface CompetitionRepository : CrudRepository<Competition, Long> {
    fun findAllBySource(source: Source): ArrayList<Competition>
}

interface SeasonRepository : CrudRepository<Season, Long> {
    fun findBySource(source: Source): ArrayList<Season>
}

interface CountryRepository : CrudRepository<Country, Long>
