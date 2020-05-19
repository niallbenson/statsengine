package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.repos.engine.*
import io.mockk.every
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StatsBombEntityServiceTest {

    private lateinit var sourceRepository: SourceRepository
    private lateinit var competitionRepository: CompetitionRepository
    private lateinit var countryRepository: CountryRepository
    private lateinit var seasonRepository: SeasonRepository
    private lateinit var competitionSeasonRepository: CompetitionSeasonRepository
    private lateinit var matchRepository: MatchRepository
    private lateinit var teamRepository: TeamRepository
    private lateinit var managerRepository: ManagerRepository
    private lateinit var matchMetadataRepository: MatchMetadataRepository
    private lateinit var competitionStageRepository: CompetitionStageRepository
    private lateinit var stadiumRepository: StadiumRepository
    private lateinit var refereeRepository: RefereeRepository

    @Test
    fun getStatsBombSource() {

    }

    @Test
    fun save() {
    }

    @Test
    fun getCompetitionsBySouce() {
    }

    @Test
    fun getCompetitionByExternalId() {
    }

    @Test
    fun getOrCreateCompetition() {
    }

    @Test
    fun testSave() {
    }

    @Test
    fun getAllCountries() {
    }

    @Test
    fun getCountryByExternalId() {
    }

    @Test
    fun getOrCreateCountry() {
    }

    @Test
    fun testGetOrCreateCountry() {
    }

    @Test
    fun testSave1() {
    }

    @Test
    fun getSeasonsBySource() {
    }

    @Test
    fun getSeasonByExternalId() {
    }

    @Test
    fun getOrCreateSeason() {
    }

    @Test
    fun testGetOrCreateSeason() {
    }

    @Test
    fun testSave2() {
    }

    @Test
    fun getCompetitionSeasonsForCompetitions() {
    }

    @Test
    fun getCompetitionSeasons() {
    }

    @Test
    fun getOrCreateCompetitionSeason() {
    }

    @Test
    fun testSave3() {
    }

    @Test
    fun getMatchesForCompetitionExternalIds() {
    }

    @Test
    fun getMatchByExternalId() {
    }

    @Test
    fun testSave4() {
    }

    @Test
    fun getTeamByExternalId() {
    }

    @Test
    fun getManagerById() {
    }

    @Test
    fun getOrCreateTeam() {
    }

    @Test
    fun testSave5() {
    }

    @Test
    fun getOrCreateManager() {
    }

    @Test
    fun testSave6() {
    }

    @Test
    fun getOrCreateCompetitionStage() {
    }

    @Test
    fun testSave7() {
    }

    @Test
    fun getOrCreateStadium() {
    }

    @Test
    fun testSave8() {
    }

    @Test
    fun getOrCreateMatchMetaData() {
    }

    @Test
    fun testSave9() {
    }

    @Test
    fun getOrCreateReferee() {
    }
}