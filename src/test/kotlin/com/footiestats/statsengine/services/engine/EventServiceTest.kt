package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.EventMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
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
internal class EventServiceTest {

    private val mockObjects = EngineMockObjects()

    @RelaxedMockK
    private lateinit var eventMapper: EventMapper

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var service: EventService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getPlayerMatchEvents expect exception when matchId is not greater than zero`() {
        val playerId = 1L
        val matchId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchEvents(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchEvents expect exception when playerId is not greater than zero`() {
        val playerId = -1L
        val matchId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchEvents(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchEvents expect one Event for valid inputs`() {
        val playerId = 1L
        val matchId = 1L

        val mockEvent = mockObjects.mockEvent()

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                arrayListOf(mockEvent)

        val result = service.getPlayerMatchEvents(playerId, matchId)

        assert(result.size == 1)
    }

    @Test
    fun `getMatchEventsByType expect exception when matchId is not greater than zero`() {
        val matchId = -1L
        val eventTypeid = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchEventsByType(matchId, eventTypeid)
        }
    }

    @Test
    fun `getMatchEventsByType expect exception when eventTypeId is not greater than zero`() {
        val matchId = 1L
        val eventTypeid = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchEventsByType(matchId, eventTypeid)
        }
    }

    @Test
    fun `getMatchEventsByType expect one Event for valid inputs`() {
        val matchId = 1L
        val eventTypeId = 1L

        every { eventRepository.findAllByMatch_IdAndType_Id(matchId, eventTypeId) } returns
                arrayListOf(mockObjects.mockEvent())

        val result = service.getMatchEventsByType(matchId, eventTypeId)

        assert(result.size == 1)
    }

    @Test
    fun `getMatchGoals expect exception when matchId is not greater than zero`() {
        val matchId = -1L
        val teamId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchGoals(matchId, teamId)
        }
    }

    @Test
    fun `getMatchGoals expect exception when teamId is not greater than zero`() {
        val matchId = 1L
        val teamId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchGoals(matchId, teamId)
        }
    }

    @Test
    fun `getMatchGoals expect one event for valid input`() {
        val matchId = 1L
        val teamId = 1L

        every {
            eventRepository.findAllByMatch_IdAndType_IdAndShot_Outcome_IdAndEventTeam_IdOrderByEventIndex(
                    matchId, EventTypeEnum.SHOT.id, OutcomeEnum.GOAL.id, teamId)
        } returns
                arrayListOf(mockObjects.mockEvent())

        val result = service.getMatchGoals(matchId, teamId)

        assert(result.size == 1)
    }
}