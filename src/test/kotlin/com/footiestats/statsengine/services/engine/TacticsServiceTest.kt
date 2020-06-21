package com.footiestats.statsengine.services.engine


import com.footiestats.statsengine.dtos.engine.mappers.TacticsMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.repos.engine.TacticsRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TacticsServiceTest {

    private val mockEventObjectObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var tacticsMapper: TacticsMapper

    @RelaxedMockK
    private lateinit var tacticsRepository: TacticsRepository

    @InjectMockKs
    private lateinit var service: TacticsService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getMatchTeamTacticsDto expect exception when matchId is not greater than zero`() {
        val matchId = -1L
        val teamId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamTacticsDto(matchId, teamId)
        }
    }

    @Test
    fun `getMatchTeamTacticsDto expect exception when teamId is not greater than zero`() {
        val matchId = 1L
        val teamId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamTacticsDto(matchId, teamId)
        }
    }

    @Test
    fun `getMatchTeamTacticsDto expect result for valid inputs`() {
        val matchId = 1L
        val teamId = 1L

        every {
            tacticsRepository.findAllByEvent_Match_IdAndEvent_EventTeam_Id(matchId, teamId)
        } returns arrayListOf(mockEventObjectObjects.mockTactics())

        val result = service.getMatchTeamTacticsDto(matchId, teamId)

        assert(result.size == 1)
    }
}