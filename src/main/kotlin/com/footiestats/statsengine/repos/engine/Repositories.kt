package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository


interface SourceRepository : PagingAndSortingRepository<Source, Long> {
    fun findByName(name: String): Source?
}

interface CompetitionRepository : PagingAndSortingRepository<Competition, Long> {
    fun findAllBySource(source: Source): ArrayList<Competition>
    fun findBySourceExternalId(id: String): Competition?
}

interface SeasonRepository : PagingAndSortingRepository<Season, Long> {
    fun findBySource(source: Source): ArrayList<Season>
    fun findByName(name: String): Season?
}

interface CountryRepository : PagingAndSortingRepository<Country, Long> {
    override fun findAll(): ArrayList<Country>
    fun findBySourceExternalId(id: String): Country?
    fun findByName(name: String): Country?
}

interface CompetitionStageRepository : PagingAndSortingRepository<CompetitionStage, Long> {
    override fun findAll(): ArrayList<CompetitionStage>
}

interface ManagerRepository : PagingAndSortingRepository<Manager, Long> {
    override fun findAll(): ArrayList<Manager>
    fun findBySourceExternalId(id: String): Manager?
}

interface MatchRepository : PagingAndSortingRepository<Match, Long> {
    override fun findAll(): ArrayList<Match>

    @Query("from Match m join fetch m.competition c where c.sourceExternalId in ?1")
    fun findAllByCompetitionSourceExternalId(ids: Iterable<String>): ArrayList<Match>
}

interface MetadataRepository : PagingAndSortingRepository<Metadata, Long> {
    override fun findAll(): ArrayList<Metadata>
}

interface RefereeRepository : PagingAndSortingRepository<Referee, Long> {
    override fun findAll(): ArrayList<Referee>
}

interface StadiumRepository : PagingAndSortingRepository<Stadium, Long> {
    override fun findAll(): ArrayList<Stadium>
}

interface TeamRepository : PagingAndSortingRepository<Team, Long> {
    override fun findAll(): ArrayList<Team>
    fun findBySourceExternalId(id: String): Team?
}

interface CompetitionSeasonRepository : PagingAndSortingRepository<CompetitionSeason, Long> {
    override fun findAll(): ArrayList<CompetitionSeason>
    fun findAllByCompetitionIn(competitions: Iterable<Competition>): ArrayList<CompetitionSeason>
    fun findAllByCompetitionSource(source: Source): ArrayList<CompetitionSeason>
    fun findByCompetitionAndSeason(competition: Competition, season: Season): CompetitionSeason?
}