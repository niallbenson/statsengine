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
    fun findAllByCompetitionSeason(competitionSeason: CompetitionSeason): ArrayList<Match>

    @Query("from Match m join fetch m.competitionSeason cs where cs.competition.id = ?1 and cs.season.id = ?2")
    fun findAllByCompetitionAndSeason(competitionId: Long, seasonId: Long): ArrayList<Match>
}

interface MatchMetadataRepository : PagingAndSortingRepository<MatchMetadata, Long> {
    override fun findAll(): ArrayList<MatchMetadata>

    fun findByDataVersionAndShotFidelityVersionAndXyFidelityVersion(
            dataVersion: String?,
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
    fun findAllByCompetition_Source(source: Source): Iterable<CompetitionSeason>
    fun findByCompetitionAndSeason(competition: Competition, season: Season): CompetitionSeason?
}

interface MatchLineupRepository : PagingAndSortingRepository<MatchLineup, Long> {
    override fun findAll(): ArrayList<MatchLineup>
    fun findByMatchAndTeam(match: Match, team: Team): MatchLineup?
}

interface PlayerRepository : PagingAndSortingRepository<Player, Long> {
    fun findBySourceExternalId(id: String): Player?
}

interface LineupPlayerRepository : PagingAndSortingRepository<LineupPlayer, Long> {

}