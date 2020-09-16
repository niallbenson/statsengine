package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.PlayerMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.PlayerRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import com.footiestats.statsengine.services.engine.eventanalysis.UberEventAnalysisService
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PlayerMatchTimelineServiceTest {

    private val mockObjects = EngineMockObjects()
    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var playerMapper: PlayerMapper

    @RelaxedMockK
    private lateinit var playerRepository: PlayerRepository

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @RelaxedMockK
    private lateinit var passAnalysisService: PassAnalysisService

    @RelaxedMockK
    private lateinit var uberEventAnalysisService: UberEventAnalysisService

    @RelaxedMockK
    lateinit var shotService: ShotService

    @InjectMockKs
    private lateinit var service: PlayerMatchTimelineService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getPlayerMatchTimelineDto expect exception when playerId is not greater than zero`() {
        val playerId = -1L
        val matchId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchTimelineDto(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchTimelineDto expect exception when matchId is not greater than zero`() {
        val playerId = 1L
        val matchId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchTimelineDto(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchTimelineDto expect Dto player name to be empty string`() {
        val playerId = 1L
        val matchId = 1L

        every { playerRepository.findByIdOrNull(playerId) } returns
                mockObjects.player()

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf()

        val result = service.getPlayerMatchTimelineDto(playerId, matchId)

        assert(result.player.name == "")
    }

    @Test
    fun `getPlayerMatchTimelineDto expect timeline with 3 events`() {
        val playerId = 1L
        val matchId = 1L

        every { playerRepository.findByIdOrNull(playerId) } returns
                mockObjects.player()

        val pass = mockEventObjects.passEvent()
        val ballReceipt = mockEventObjects.ballReceipt(1)
        val goal = mockEventObjects.shotEventGoalOutcome()

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(pass, ballReceipt, goal)

        val result = service.getPlayerMatchTimelineDto(playerId, matchId)

        assert(result.items.size == 3)
    }

}
