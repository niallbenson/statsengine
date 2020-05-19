package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombMatchMapper
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombFileUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Test

internal class StatsBombMatchFeedServiceTest {

    private val matchDataJsonPath = "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/matches.json"

    @RelaxedMockK
    private lateinit var entityService: StatsBombEntityService

    @RelaxedMockK
    private lateinit var restService: StatsBombRestService

    @InjectMockKs
    private lateinit var service: StatsBombMatchFeedService

    @Test
    fun run() {
        MockKAnnotations.init(this)

        val competition = mockCompetition()
        val season = mockSeason()

        every { entityService.getCompetitionSeasons() } returns
                arrayListOf(CompetitionSeason(competition, season))

        val json = StatsBombFileUtils.readFile(matchDataJsonPath)

        every {
            restService.getStatsBombMatches(
                    competition.sourceExternalId, season.sourceExternalId)
        } returns
                StatsBombMatchMapper.fromJson(json)

        every {
            entityService.getMatchByExternalId(any<String>())
        } returns null

        every {
            entityService.getCompetitionByExternalId(competition.sourceExternalId)
        } returns competition

        every {
            entityService.getSeasonByExternalId(season.sourceExternalId)
        } returns season

        every {
            entityService.getOrCreateStadium(any<StatsBombStadium>())
        } returns mockStadium()

        every {
            entityService.getOrCreateReferee(any<StatsBombReferee>())
        } returns mockReferee()

        every {
            entityService.getOrCreateTeam(any<StatsBombTeam>())
        } returns mockTeam()

        every {
            entityService.getOrCreateMatchMetaData(any<StatsBombMatchMetadata>())
        } returns mockMatchMetaData()

        every {
            entityService.getOrCreateCompetitionStage(any<StatsBombCompetitionStage>())
        } returns mockCompetitionStage()

        every {
            entityService.getStatsBombSource()
        } returns mockSource()

        service.run()
    }

    private fun mockSource() = Source("source")

    private fun mockCompetition() =
            Competition(
                    mockCountry(),
                    "Competition name",
                    Gender.MALE,
                    mockSource(),
                    "1",
                    1L)

    private fun mockSeason() = Season("1999-00", mockSource(), "1", 1L)

    private fun mockStadium() = Stadium("Stadium", mockCountry(), mockSource(), "1")

    private fun mockCountry() = Country("England", mockSource(), "1")

    private fun mockReferee() = Referee("Referee", mockCountry(), mockSource(), "1")

    private fun mockManager() =
            Manager("Manage", null, null, mockCountry(), mockSource(), "1")

    private fun mockTeam() =
            Team(
                    "Team",
                    Gender.MALE,
                    null,
                    mockCountry(),
                    arrayListOf(mockManager()),
                    mockSource(),
                    "1"
            )

    private fun mockMatchMetaData() =
            MatchMetadata("1.0", "1.0", "1.0", mockSource())

    private fun mockCompetitionStage() = CompetitionStage("Stage", mockSource(), "1")

}