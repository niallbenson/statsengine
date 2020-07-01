package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.dtos.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.dtos.statsbomb.StatsBombCountry
import com.footiestats.statsengine.dtos.statsbomb.StatsBombManager
import com.footiestats.statsengine.dtos.statsbomb.StatsBombTeam
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.repos.engine.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StatsBombBaseEntityServiceTest {

    private val statsBombSourceName = "StatsBomb"

    private val mockObjects = EngineMockObjects()

    @RelaxedMockK private lateinit var sourceRepository: SourceRepository
    @RelaxedMockK private lateinit var competitionRepository: CompetitionRepository
    @RelaxedMockK private lateinit var countryRepository: CountryRepository
    @RelaxedMockK private lateinit var seasonRepository: SeasonRepository
    @RelaxedMockK private lateinit var competitionSeasonRepository: CompetitionSeasonRepository
    @RelaxedMockK private lateinit var matchRepository: MatchRepository
    @RelaxedMockK private lateinit var teamRepository: TeamRepository
    @RelaxedMockK private lateinit var managerRepository: ManagerRepository
    @RelaxedMockK private lateinit var matchMetadataRepository: MatchMetadataRepository
    @RelaxedMockK private lateinit var competitionStageRepository: CompetitionStageRepository
    @RelaxedMockK private lateinit var stadiumRepository: StadiumRepository
    @RelaxedMockK private lateinit var refereeRepository: RefereeRepository
    @RelaxedMockK private lateinit var matchLineupRepository: MatchLineupRepository
    @RelaxedMockK private lateinit var playerRepository: PlayerRepository
    @RelaxedMockK private lateinit var lineupPlayerRepository: LineupPlayerRepository

    @InjectMockKs
    private lateinit var service: StatsBombBaseEntityService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun getStatsBombSource() {
        every { sourceRepository.findByName(statsBombSourceName) } returns mockObjects.source()

        val source = service.getStatsBombSource()

        assert(source.name == "source")
    }

    @Test
    fun getCompetitionsBySource() {
        val source = mockObjects.source()
        val competition = mockObjects.competition()

        every { competitionRepository.findAllBySource(source) } returns
                arrayListOf(competition)

        val competitions = service.getCompetitionsBySource(source)

        assert(competitions.size == 1)
        assert(competitions[0].name == competition.name)
    }

    @Test
    fun getCompetitionByExternalId() {
        val id = "87"
        val competition = mockObjects.competition()

        every { competitionRepository.findBySourceExternalId(id) } returns
                competition

        val result = service.getCompetitionByExternalId(id)

        assert(result?.name == competition.name)
    }

    @Test
    fun `getOrCreateCompetition when competition already exists then expect same name`() {
        val statsBombCompetition = StatsBombCompetition(
                98, 35, "England", "EPL", "MALE",
                "2019/20", "2020-06-27 14:00:00", "yes")

        val competition = mockObjects.competition()

        every { competitionRepository.findBySourceExternalId(statsBombCompetition.competitionId.toString()) } returns
                competition

        every { competitionRepository.save(competition) } returns
                competition

        val competitionResult = service.getOrCreateCompetition(statsBombCompetition)

        assert(competitionResult.name == competition.name)
    }

    @Test
    fun `getOrCreateCompetition when competition doesnt exists then create and expect same name`() {
        val statsBombCompetition = StatsBombCompetition(
                98, 35, "England", "EPL", "MALE",
                "2019/20", "2020-06-27 14:00:00", "yes")

        val competition = mockObjects.competition(statsBombCompetition.competitionName)

        every { competitionRepository.findBySourceExternalId(statsBombCompetition.competitionId.toString()) } returns
                null

        every { competitionRepository.save(any<Competition>()) } returns
                competition

        val competitionResult = service.getOrCreateCompetition(statsBombCompetition)

        assert(competitionResult.name == statsBombCompetition.competitionName)
    }

    @Test
    fun getAllCountries() {
        val country = mockObjects.country()

        every { countryRepository.findAll() } returns
                arrayListOf(country)

        val countriesResult = service.getAllCountries()

        assert(countriesResult.size == 1)
        assert(countriesResult[0].name == country.name)
    }

    @Test
    fun `getOrCreateCountry by name when country already exists expect same name`() {
        val country = mockObjects.country()

        every { countryRepository.findByName(country.name) } returns
                country

        val countryResult = service.getOrCreateCountry(country.name)

        assert(countryResult.name == country.name)
    }

    @Test
    fun `getOrCreateCountry by name when country doesnt exist then create with same name`() {
        val country = mockObjects.country()
        val countryName = country.name

        every { countryRepository.findByName(countryName) } returns
                null

        every { countryRepository.save(any<Country>()) } returns country

        val countryResult = service.getOrCreateCountry(countryName)

        assert(countryResult.name == country.name)
    }

    @Test
    fun `getOrCreateCountry from statsbomb when country already exists expect same name`() {
        val statsBombCountry = StatsBombCountry(897, "England")
        val source = mockObjects.source()

        val country = Country(statsBombCountry.name, "1", source)

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                country

        val countryResult = service.getOrCreateCountry(statsBombCountry)

        assert(countryResult.name == country.name)
    }

    @Test
    fun `getOrCreateCountry from statsbomb when country doesnt exist then create with same name`() {
        val statsBombCountry = StatsBombCountry(897, "England")
        val source = mockObjects.source()

        val country = Country(statsBombCountry.name, "1", source)

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                null

        every { countryRepository.findByName(statsBombCountry.name) } returns
                null

        every { countryRepository.save(any<Country>()) } returns
                country

        val countryResult = service.getOrCreateCountry(statsBombCountry)

        assert(countryResult.name == country.name)
    }

    @Test
    fun `getOrCreateCountry when statsbomb country exists but has a different name then update and return entity`() {
        val statsBombCountry = StatsBombCountry(897, "England")
        val source = mockObjects.source()

        val country = Country("Incorrect name", "1", source)

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                country

        every { countryRepository.findByName(statsBombCountry.name) } returns
                null

        every { countryRepository.save(any<Country>()) } returns
                country

        val countryResult = service.getOrCreateCountry(statsBombCountry)

        assert(countryResult.name == statsBombCountry.name)

    }

    @Test
    fun getSeasonsBySource() {
        val source = mockObjects.source()
        val season = mockObjects.season()

        every { seasonRepository.findBySource(source) } returns
                arrayListOf(season)

        val result = service.getSeasonsBySource(source)

        assert(result.size == 1)
        assert(result[0].name == "season")
    }

    @Test
    fun getSeasonByExternalId() {
        val id = "75"
        val season = mockObjects.season()

        every { seasonRepository.findBySourceExternalId(id) } returns
                season

        val result = service.getSeasonByExternalId(id)

        assert(result?.name == "season")
    }

    @Test
    fun `getOrCreateSeason when exists by external id then expect same name in result`() {
        val source = mockObjects.source()
        val season = Season("2020/21", "42", source)

        every { seasonRepository.findBySourceExternalId(season.sourceExternalId) } returns
                season

        val result = service.getOrCreateSeason(season.sourceExternalId, season.name)

        assert(result.name == season.name)
    }

    @Test
    fun `getOrCreateSeason when doesnt exist by external id then expect same name in result`() {
        val source = mockObjects.source()
        val season = Season("2020/21", "42", source)

        every { seasonRepository.findBySourceExternalId(season.sourceExternalId) } returns
                null

        every { seasonRepository.save(any<Season>()) } returns season

        val result = service.getOrCreateSeason(season.sourceExternalId, season.name)

        assert(result.name == season.name)
    }

    @Test
    fun `getOrCreateSeason when exists by external id but with name change then expect new name in result`() {
        val source = mockObjects.source()
        val season = Season("2020/21", "42", source)

        every { seasonRepository.findBySourceExternalId(season.sourceExternalId) } returns
                Season("Old name", season.sourceExternalId, source)

        every { seasonRepository.save(any<Season>()) } returns season

        val result = service.getOrCreateSeason(season.sourceExternalId, season.name)

        assert(result.name == season.name)
    }

    @Test
    fun getCompetitionSeasonsForCompetitions() {
        val competitionSet = setOf(mockObjects.competition())
        val competitionSeasonList = arrayListOf(mockObjects.competitionSeason())

        every { competitionSeasonRepository.findAllByCompetitionIn(competitionSet)} returns
                competitionSeasonList

        val result = service.getCompetitionSeasonsForCompetitions(competitionSet)

        assert(result.size == 1)
        assert(result[0].season.name == competitionSeasonList[0].season.name)
    }

    @Test
    fun getCompetitionSeasons() {
        val competitionSeasonList = arrayListOf(mockObjects.competitionSeason())

        every { competitionSeasonRepository.findAllByCompetition_Source(any<Source>()) } returns
                competitionSeasonList

        val result = service.getCompetitionSeasons()

        assert(result.size == 1)
        assert(result[0].season.name == competitionSeasonList[0].season.name)
    }

    @Test
    fun `getOrCreateCompetitionSeason when exists then return`() {
        val competition = mockObjects.competition()
        val season = mockObjects.season()

        val competitionSeason = CompetitionSeason(competition, season)

        every { competitionSeasonRepository.findByCompetitionAndSeason(competition, season) } returns
                competitionSeason

        val result = service.getOrCreateCompetitionSeason(competition, season)

        assert(result.season.name == season.name)
        assert(result.competition.name == competition.name)
    }

    @Test
    fun `getOrCreateCompetitionSeason when doesnt exist then create and return`() {
        val competition = mockObjects.competition()
        val season = mockObjects.season()

        val competitionSeason = CompetitionSeason(competition, season)

        every { competitionSeasonRepository.findByCompetitionAndSeason(competition, season) } returns
                null

        every { competitionSeasonRepository.save(any<CompetitionSeason>()) } returns
                competitionSeason

        val result = service.getOrCreateCompetitionSeason(competition, season)

        assert(result.season.name == season.name)
        assert(result.competition.name == competition.name)
    }

    @Test
    fun getMatchesForCompetitionSeason() {
        val competition = mockObjects.competition()
        val season = mockObjects.season()

        val competitionSeason = CompetitionSeason(competition, season)

        val match = mockObjects.match()

        every { matchRepository.findAllByCompetitionSeason(competitionSeason) } returns
                arrayListOf(match)

        val result = service.getMatchesForCompetitionSeason(competitionSeason)

        assert(result.size == 1)
        assert(result[0].homeTeam.id == match.homeTeam.id)
        assert(result[0].awayTeam.id == match.awayTeam.id)
    }

    @Test
    fun getMatchByExternalId() {
        val id = "45"
        val match = mockObjects.match()

        every { matchRepository.findBySourceExternalId(id) } returns
                match

        val result = service.getMatchByExternalId(id)

        assert(result?.homeTeam?.id == match.homeTeam.id)
        assert(result?.awayTeam?.id == match.awayTeam.id)
    }

    @Test
    fun getTeamByExternalId() {
        val team = mockObjects.team1()

        every { teamRepository.findBySourceExternalId(team.sourceExternalId) } returns
                team

        val result = service.getTeamByExternalId(team.sourceExternalId)

        assert(result?.name == team.name)
    }

    @Test
    fun `getOrCreateTeam when team already exists expect team with same name`() {

        val statsBombCountry = getStatsBombCountry()
        val statsBombManager = getStatsBombManager(statsBombCountry)

        val statsBombTeam = getStatsBombTeam(statsBombCountry, statsBombManager)

        val source = mockObjects.source()
        val country = Country(statsBombCountry.name, statsBombCountry.id.toString(), source)
        val manager = Manager(statsBombManager.name, statsBombManager.nickname, LocalDate.parse(statsBombManager.dob),
                        statsBombManager.toString(), country, source)

        val team = Team(
                statsBombTeam.teamName, Gender.MALE, statsBombTeam.teamGroup, statsBombTeam.teamId.toString(),
                country, arrayListOf(manager), source)

        every { teamRepository.findBySourceExternalId(statsBombTeam.teamId.toString()) } returns
                team

        every { managerRepository.findBySourceExternalId(statsBombManager.id.toString()) } returns
                manager

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                country

        val result = service.getOrCreateTeam(statsBombTeam)

        assert(result.name == team.name)
    }

    @Test
    fun `getOrCreateTeam when team doesnt exist then create and expect result with same name`() {

        val statsBombCountry = getStatsBombCountry()
        val statsBombManager = getStatsBombManager(statsBombCountry)

        val statsBombTeam = getStatsBombTeam(statsBombCountry, statsBombManager)

        val source = mockObjects.source()
        val country = Country(statsBombCountry.name, statsBombCountry.id.toString(), source)
        val manager = Manager(statsBombManager.name, statsBombManager.nickname, LocalDate.parse(statsBombManager.dob),
                statsBombManager.toString(), country, source)

        val team = Team(
                statsBombTeam.teamName, Gender.MALE, statsBombTeam.teamGroup, statsBombTeam.teamId.toString(),
                country, arrayListOf(manager), source)

        every { teamRepository.findBySourceExternalId(statsBombTeam.teamId.toString()) } returns
                null

        every { teamRepository.save(any<Team>()) } returns
                team

        every { managerRepository.findBySourceExternalId(statsBombManager.id.toString()) } returns
                manager

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                country

        val result = service.getOrCreateTeam(statsBombTeam)

        assert(result.name == team.name)
    }

    @Test
    fun `getOrCreateTeam when team exists but has changes then update and expect result with same name`() {

        val statsBombCountry = getStatsBombCountry()
        val statsBombManager = getStatsBombManager(statsBombCountry)

        val statsBombTeam = getStatsBombTeam(statsBombCountry, statsBombManager)

        val source = mockObjects.source()
        val country = Country(statsBombCountry.name, statsBombCountry.id.toString(), source)
        val manager = Manager(statsBombManager.name, statsBombManager.nickname, LocalDate.parse(statsBombManager.dob),
                statsBombManager.id.toString(), country, source)

        val newManager = Manager("New Manager", null, LocalDate.MIN,
                "54564", country, source)

        val team = Team(
                statsBombTeam.teamName, Gender.MALE, statsBombTeam.teamGroup, statsBombTeam.teamId.toString(),
                country,
                arrayListOf(manager, newManager),
                source)

        every { teamRepository.findBySourceExternalId(statsBombTeam.teamId.toString()) } returns
                team

        every { teamRepository.save(any<Team>()) } returns
                team

        every { managerRepository.findBySourceExternalId(statsBombManager.id.toString()) } returns
                manager

        every { countryRepository.findBySourceExternalId(statsBombCountry.id.toString()) } returns
                country

        val result = service.getOrCreateTeam(statsBombTeam)

        assert(result.name == team.name)
    }

    private fun getStatsBombTeam(statsBombCountry: StatsBombCountry, statsBombManager: StatsBombManager) =
            StatsBombTeam(
                    53, "team name", "MALE", "group", statsBombCountry, arrayListOf(statsBombManager))

    private fun getStatsBombManager(statsBombCountry: StatsBombCountry) =
            StatsBombManager(56, "A Manager", "AM", "1980-05-01", statsBombCountry)

    private fun getStatsBombCountry() = StatsBombCountry(45, "Country")

    @Test
    fun getManagerById() {
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

    @Test
    fun testSave10() {
    }

    @Test
    fun getOrCreateMatchLineup() {
    }

    @Test
    fun getPlayerByExternalId() {
    }

    @Test
    fun getOrCreatePlayer() {
    }
}