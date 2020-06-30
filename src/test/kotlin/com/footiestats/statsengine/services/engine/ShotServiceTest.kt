package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.ShotMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.repos.engine.EventRepository
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
internal class ShotServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var shotMapper: ShotMapper

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var service: ShotService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getMatchTeamShotsDtos expect exception when matchId is not greater than zero`() {
        val matchId = -1L
        val teamId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamShotsDtos(matchId, teamId)
        }
    }

    @Test
    fun `getMatchTeamShotsDtos expect exception when teamId is not greater than zero`() {
        val matchId = 1L
        val teamId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamShotsDtos(matchId, teamId)
        }
    }

    @Test
    fun `getMatchTeamShotsDtos expect one result for valid inputs`() {
        val matchId = 1L
        val teamId = 1L

        every {
            eventRepository.findAllByMatch_IdAndType_IdAndEventTeam_Id(matchId, EventTypeEnum.SHOT.id, teamId)
        } returns setOf(mockEventObjects.shotEventGoalOutcome())

        val result = service.getMatchTeamShotsDtos(matchId, teamId)

        assert(result.size == 1)
    }
}