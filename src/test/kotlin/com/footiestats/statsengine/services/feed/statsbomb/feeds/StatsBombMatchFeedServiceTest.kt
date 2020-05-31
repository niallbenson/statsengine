package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombMatchMapper
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
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
    private lateinit var baseEntityService: StatsBombBaseEntityService

    @RelaxedMockK
    private lateinit var restService: StatsBombRestService

    @InjectMockKs
    private lateinit var service: StatsBombMatchFeedService

    @Test
    fun run() {
        MockKAnnotations.init(this)

        val competition = mockCompetition()
        val season = mockSeason()

        every { baseEntityService.getCompetitionSeasons() } returns
                arrayListOf(CompetitionSeason(competition, season))

        val json = StatsBombFileUtils.readFile(matchDataJsonPath)

        every {
            restService.getStatsBombMatches(
                    competition.sourceExternalId, season.sourceExternalId)
        } returns
                StatsBombMatchMapper.fromJson(json)

        every {
            baseEntityService.getMatchByExternalId(any<String>())
        } returns null

        every {
            baseEntityService.getCompetitionByExternalId(competition.sourceExternalId)
        } returns competition

        every {
            baseEntityService.getSeasonByExternalId(season.sourceExternalId)
        } returns season

        every {
            baseEntityService.getOrCreateStadium(any<StatsBombStadium>())
        } returns mockStadium()

        every {
            baseEntityService.getOrCreateReferee(any<StatsBombReferee>())
        } returns mockReferee()

        every {
            baseEntityService.getOrCreateTeam(any<StatsBombTeam>())
        } returns mockTeam()

        every {
            baseEntityService.getOrCreateMatchMetaData(any<StatsBombMatchMetadata>())
        } returns mockMatchMetaData()

        every {
            baseEntityService.getOrCreateCompetitionStage(any<StatsBombCompetitionStage>())
        } returns mockCompetitionStage()

        every {
            baseEntityService.getStatsBombSource()
        } returns mockSource()

        service.run()
    }

    private fun mockSource() = Source("source")

    private fun mockCompetition(): Competition {
        val competition = Competition(
                "Competition name",
                Gender.MALE,
                "1",
                mockCountry(),
                mockSource())

        return competition
    }

    private fun mockSeason() = Season("1999-00", "1", mockSource())

    private fun mockStadium() = Stadium("Stadium", "1", mockCountry(), mockSource())

    private fun mockCountry() = Country("England", "1", mockSource())

    private fun mockReferee() = Referee("Referee", "1", mockCountry(), mockSource())

    private fun mockManager() =
            Manager("Manager", null, null, "1", mockCountry(), mockSource())

    private fun mockTeam() = Team(
            "Team",
            Gender.MALE,
            null,
            "1",
            mockCountry(),
            arrayListOf(mockManager()),
            mockSource()
    )

    private fun mockMatchMetaData() =
            MatchMetadata("1.0", "1.0", "1.0", mockSource())

    private fun mockCompetitionStage() = CompetitionStage("Stage", "1", mockSource())

}