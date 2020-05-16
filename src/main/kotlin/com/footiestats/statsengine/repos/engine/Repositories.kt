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
    fun findBySourceExternalId(id: String): Season?
    fun findByName(name: String): Season?
}

interface CountryRepository : PagingAndSortingRepository<Country, Long> {
    override fun findAll(): ArrayList<Country>
    fun findBySourceExternalId(id: String): Country?
    fun findByName(name: String): Country?
}

interface CompetitionStageRepository : PagingAndSortingRepository<CompetitionStage, Long> {
    override fun findAll(): ArrayList<CompetitionStage>
    fun findBySourceExternalId(id: String): CompetitionStage?
}

interface ManagerRepository : PagingAndSortingRepository<Manager, Long> {
    override fun findAll(): ArrayList<Manager>
    fun findBySourceExternalId(id: String): Manager?
}

interface MatchRepository : PagingAndSortingRepository<Match, Long> {
    override fun findAll(): ArrayList<Match>
    fun findBySourceExternalId(id: String): Match?

    @Query("from Match m join fetch m.competition c where c.sourceExternalId in ?1")
    fun findAllByCompetitionSourceExternalId(ids: Iterable<String>): ArrayList<Match>
}

interface MatchMetadataRepository : PagingAndSortingRepository<MatchMetadata, Long> {
    override fun findAll(): ArrayList<MatchMetadata>

    @Query("from MatchMetadata m " +
            "where m.dataVersion = ?1 " +
            "and (m.shotFidelityVersion = ?2 or (m.shotFidelityVersion is null and ?2 is null)) " +
            "and (m.xyFidelityVersion = ?3 or (m.xyFidelityVersion is null and ?3 is null))")
    fun findByValues(
            dataVersion: String,
            shotFidelityVersion: String?,
            xyFidelityVersion: String?
    ): MatchMetadata?
}

interface RefereeRepository : PagingAndSortingRepository<Referee, Long> {
    override fun findAll(): ArrayList<Referee>
    fun findBySourceExternalId(id: String): Referee?
}

interface StadiumRepository : PagingAndSortingRepository<Stadium, Long> {
    override fun findAll(): ArrayList<Stadium>
    fun findBySourceExternalId(id: String): Stadium?
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