package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.EventMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Team
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.MatchRepository
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EventServiceTest {

    private val mockObjects = EngineMockObjects()
    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var eventMapper: EventMapper

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @RelaxedMockK
    private lateinit var matchRepository: MatchRepository

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

        val mockEvent = mockEventObjects.event()

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(mockEvent)

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
                setOf(mockEventObjects.event())

        val result = service.getMatchEventsByType(matchId, eventTypeId)

        assert(result.size == 1)
    }

    @Test
    fun `getMatchGoals expect exception when matchId is not greater than zero`() {
        val matchId = -1L
        val teamId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamGoals(matchId, teamId)
        }
    }

    @Test
    fun `getMatchGoals expect exception when teamId is not greater than zero`() {
        val matchId = 1L
        val teamId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getMatchTeamGoals(matchId, teamId)
        }
    }

    @Test
    fun `getMatchGoals expect one event for valid input`() {
        val matchId = 1L
        val teamId = 1L
        val opposingTeamId = 2L

        every {
            eventRepository.findAllByMatch_IdAndType_IdAndShot_Outcome_IdAndEventTeam_IdOrderByEventIndex(
                    matchId, EventTypeEnum.SHOT.id, OutcomeEnum.GOAL.id, teamId)
        } returns
                setOf(mockEventObjects.event())

        every { matchRepository.findByIdOrNull(matchId) } returns mockMatch()

        every { eventRepository.findAllByMatch_IdAndType_IdAndEventTeam_Id(
                matchId, EventTypeEnum.OWN_GOAL_AGAINST.id, opposingTeamId) } returns
                setOf(mockEventObjects.event())

        val result = service.getMatchTeamGoals(matchId, teamId)

        assert(result.size == 2)
    }

    private fun mockMatch() = Match(
            LocalDateTime.MIN, 1, 1, "available", LocalDateTime.MAX, 1,
            "1", mockObjects.competitionSeason(), mockTeam(1), mockTeam(2), mutableListOf(),
            mutableListOf(), mockObjects.matchMetadata(), mockObjects.competitionStage(), null, null,
            mockObjects.source(), 1)

    private fun mockTeam(id: Long) = Team(
            "Team $id", Gender.MALE, "group", id.toString(), mockObjects.country(),
            mutableListOf(), mockObjects.source(), id)
}