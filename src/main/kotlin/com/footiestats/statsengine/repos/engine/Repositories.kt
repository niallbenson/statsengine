package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RestResource
import kotlin.Metadata

interface SourceRepository : PagingAndSortingRepository<Source, Long> {
    fun findByName(name: String): Source?
}

interface CompetitionRepository : PagingAndSortingRepository<Competition, Long> {
    fun findAllBySource(source: Source): ArrayList<Competition>
}

interface SeasonRepository : PagingAndSortingRepository<Season, Long> {
    fun findBySource(source: Source): ArrayList<Season>
}

interface CountryRepository : PagingAndSortingRepository<Country, Long> {
    override fun findAll(): ArrayList<Country>
}

interface CompetitionStageRepository : PagingAndSortingRepository<CompetitionStage, Long> {
    override fun findAll(): ArrayList<CompetitionStage>
}

interface ManagerRepository : PagingAndSortingRepository<Manager, Long> {
    override fun findAll(): ArrayList<Manager>
}

interface MatchRepository : PagingAndSortingRepository<Match, Long> {
    override fun findAll(): ArrayList<Match>
}

interface MetadataRepository : PagingAndSortingRepository<Metadata, Long> {
    override fun findAll(): ArrayList<Metadata>
}

interface RefereeyRepository : PagingAndSortingRepository<Referee, Long> {
    override fun findAll(): ArrayList<Referee>
}

interface StadiumRepository : PagingAndSortingRepository<Stadium, Long> {
    override fun findAll(): ArrayList<Stadium>
}

interface TeamRepository : PagingAndSortingRepository<Team, Long> {
    override fun findAll(): ArrayList<Team>
}