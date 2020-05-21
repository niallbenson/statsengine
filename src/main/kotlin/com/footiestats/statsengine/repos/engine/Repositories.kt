package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.PagingAndSortingRepository


interface SourceRepository : Neo4jRepository<Source, Long> {
    fun findByName(name: String): Source?
}

interface CompetitionRepository : Neo4jRepository<Competition, Long> {
    fun findAllBySource(source: Source): ArrayList<Competition>
    fun findBySourceExternalId(id: String): Competition?
}

interface SeasonRepository : Neo4jRepository<Season, Long> {
    fun findBySource(source: Source): ArrayList<Season>
    fun findBySourceExternalId(id: String): Season?
    fun findByName(name: String): Season?
}

interface CountryRepository : Neo4jRepository<Country, Long> {
    override fun findAll(): ArrayList<Country>
    fun findBySourceExternalId(id: String): Country?
    fun findByName(name: String): Country?
}

interface CompetitionStageRepository : Neo4jRepository<CompetitionStage, Long> {
    override fun findAll(): ArrayList<CompetitionStage>
    fun findBySourceExternalId(id: String): CompetitionStage?
}

interface ManagerRepository : Neo4jRepository<Manager, Long> {
    override fun findAll(): ArrayList<Manager>
    fun findBySourceExternalId(id: String): Manager?
}

interface MatchRepository : Neo4jRepository<Match, Long> {
    override fun findAll(): ArrayList<Match>
    fun findBySourceExternalId(id: String): Match?
    fun findAllByCompetitionSeason(competitionSeason: CompetitionSeason): ArrayList<Match>
}

interface MatchMetadataRepository : Neo4jRepository<MatchMetadata, Long> {
    override fun findAll(): ArrayList<MatchMetadata>

    fun findByDataVersionAndShotFidelityVersionAndXyFidelityVersion(
            dataVersion: String,
            shotFidelityVersion: String?,
            xyFidelityVersion: String?
    ): MatchMetadata?
}

interface RefereeRepository : Neo4jRepository<Referee, Long> {
    override fun findAll(): ArrayList<Referee>
    fun findBySourceExternalId(id: String): Referee?
}

interface StadiumRepository : Neo4jRepository<Stadium, Long> {
    override fun findAll(): ArrayList<Stadium>
    fun findBySourceExternalId(id: String): Stadium?
}

interface TeamRepository : Neo4jRepository<Team, Long> {
    override fun findAll(): ArrayList<Team>
    fun findBySourceExternalId(id: String): Team?
}

interface CompetitionSeasonRepository : Neo4jRepository<CompetitionSeason, Long> {
    override fun findAll(): ArrayList<CompetitionSeason>
    fun findAllByCompetitionIn(competitions: Iterable<Competition>): ArrayList<CompetitionSeason>
    fun findAllByCompetitionSource(source: Source): ArrayList<CompetitionSeason>
    fun findByCompetitionAndSeason(competition: Competition, season: Season): CompetitionSeason?
}

interface MatchLineupRepository : Neo4jRepository<MatchLineup, Long> {
    override fun findAll(): ArrayList<MatchLineup>
    fun findByMatchAndTeam(match: Match, team: Team): MatchLineup?
}

interface PlayerRepository : Neo4jRepository<Player, Long> {
    fun findBySourceExternalId(id: String): Player?
}

interface LineupPlayerRepository : Neo4jRepository<LineupPlayer, Long> {

}