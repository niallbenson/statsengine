package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
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
internal class AttackingEventSummaryServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var service: AttackingEventSummaryService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getPlayerMatchEventsTypeMap expect exception when playerId is not greater than zero`() {
        val playerId = -1L
        val matchId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchEventsTypeIdMap(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchEventsTypeMap expect exception when matchId is not greater than zero`() {
        val playerId = 1L
        val matchId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchEventsTypeIdMap(playerId, matchId)
        }
    }

    @Test
    fun `expect 2 results in map when valid inputs passed`() {
        val playerId = 1L
        val matchId = 1L

        val passEvent = mockEventObjects.mockPassEvent()

        val shotEventType = mockEventObjects.mockEventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.mockOutcome(OutcomeEnum.GOAL.id, "Goal")
        val waywardOutcome = mockEventObjects.mockOutcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val goalEvent = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 34)
        val waywardEvent = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 78)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(passEvent, goalEvent, waywardEvent)

        val result = service.getPlayerMatchEventsTypeIdMap(playerId, matchId)

        assert(result.size == 2)
        assert(result.keys.contains(passEvent.type.id))
        assert(result.keys.contains(shotEventType.id))
        assert((result[passEvent.type.id] ?: error("")).size == 1)
        assert((result[shotEventType.id] ?: error("")).size == 2)
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect exception when playerId is not greater than zero`() {
        val playerId = -1L
        val matchId = 1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchAttackSummaryDto(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect exception when matchId is not greater than zero`() {
        val playerId = 1L
        val matchId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPlayerMatchAttackSummaryDto(playerId, matchId)
        }
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect 3 goals from 5 shots`() {
        val playerId = 1L
        val matchId = 1L

        val shotEventType = mockEventObjects.mockEventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.mockOutcome(OutcomeEnum.GOAL.id, "Goal")

        val goal1Event = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 662)
        val goal2Event = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 784)
        val goal3Event = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 819)

        val waywardOutcome = mockEventObjects.mockOutcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val waywardShot1 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 564)
        val waywardShot2 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 725)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(goal1Event, goal2Event, goal3Event, waywardShot1, waywardShot2)

        val result = service.getPlayerMatchAttackSummaryDto(playerId, matchId)

        assert(result.goals == 3)
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect 6 total shots`() {
        val playerId = 1L
        val matchId = 1L

        val shotEventType = mockEventObjects.mockEventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.mockOutcome(OutcomeEnum.GOAL.id, "Goal")

        val goalShot1 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 662)
        val goalShot2 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 784)
        val goalShot3 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 819)

        val savedOutcome = mockEventObjects.mockOutcome(OutcomeEnum.SAVED.id,"Saved")
        val savedShot = mockEventObjects.mockShotEvent(shotEventType, savedOutcome, 480)

        val waywardOutcome = mockEventObjects.mockOutcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val waywardShot1 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 564)
        val waywardShot2 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 725)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(goalShot1, goalShot2, goalShot3, savedShot, waywardShot1, waywardShot2)

        val result = service.getPlayerMatchAttackSummaryDto(playerId, matchId)

        assert(result.totalShots == 6)
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect 4 out of 6 shots to be on target`() {
        val playerId = 1L
        val matchId = 1L

        val shotEventType = mockEventObjects.mockEventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.mockOutcome(OutcomeEnum.GOAL.id, "Goal")

        val goalShot1 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 662)
        val goalShot2 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 784)
        val goalShot3 = mockEventObjects.mockShotEvent(shotEventType, goalOutcome, 819)

        val savedOutcome = mockEventObjects.mockOutcome(OutcomeEnum.SAVED.id,"Saved")
        val savedShot = mockEventObjects.mockShotEvent(shotEventType, savedOutcome, 480)

        val waywardOutcome = mockEventObjects.mockOutcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val waywardShot1 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 564)
        val waywardShot2 = mockEventObjects.mockShotEvent(shotEventType, waywardOutcome, 725)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(goalShot1, goalShot2, goalShot3, savedShot, waywardShot1, waywardShot2)

        val result = service.getPlayerMatchAttackSummaryDto(playerId, matchId)

        assert(result.shotsOnTarget == 4)
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect 7 total passes`() {
        val playerId = 1L
        val matchId = 1L

        val passEvent1 = mockEventObjects.mockPassEvent(1, null, null)
        val passEvent2 = mockEventObjects.mockPassEvent(2, null, null)
        val passEvent3 = mockEventObjects.mockPassEvent(3, null, null)
        val passEvent4 = mockEventObjects.mockPassEvent(4, null, null)
        val passEvent5 = mockEventObjects.mockPassEvent(5, null, null)
        val passEvent6 = mockEventObjects.mockPassEvent(6, null, null)
        val passEvent7 = mockEventObjects.mockPassEvent(7, null, null)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(passEvent1, passEvent2, passEvent3, passEvent4, passEvent5, passEvent6, passEvent7)

        val result = service.getPlayerMatchAttackSummaryDto(playerId, matchId)

        assert(result.totalPasses == 7)
    }

    @Test
    fun `getPlayerMatchAttackSummaryDto expect 3 out of 7 total passes to be on target`() {
        val playerId = 1L
        val matchId = 1L

        val incompleteOutcome = mockEventObjects.mockOutcome(OutcomeEnum.INCOMPLETE.id, "Incomplete")

        val receipt1 = mockEventObjects.mockBallReceipt(11)
        val eventType1 = mockEventObjects.mockPassEvent(1, null, receipt1)
        val eventType2 = mockEventObjects.mockPassEvent(2, incompleteOutcome, null)
        val receipt3 = mockEventObjects.mockBallReceipt(13)
        val eventType3 = mockEventObjects.mockPassEvent(3, null, receipt3)
        val eventType4 = mockEventObjects.mockPassEvent(4, incompleteOutcome, null)
        val receipt5 = mockEventObjects.mockBallReceipt(15)
        val eventType5 = mockEventObjects.mockPassEvent(5, null, receipt5)
        val eventType6 = mockEventObjects.mockPassEvent(6, incompleteOutcome, null)
        val eventType7 = mockEventObjects.mockPassEvent(7, incompleteOutcome, null)

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                setOf(eventType1, eventType2, eventType3, eventType4, eventType5, eventType6, eventType7)

        val result = service.getPlayerMatchAttackSummaryDto(playerId, matchId)

        assert(result.accuratePasses == 3)
    }

}