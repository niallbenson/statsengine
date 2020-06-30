package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ShotAnalysisServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @InjectMockKs
    private lateinit var service: ShotAnalysisService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `isShotOnTarget expect exception when not a shot event`() {
        val event = mockEventObjects.passEvent()

        assertThrows<UnexpectedEventType> {
            service.isShotOnTarget(event)
        }
    }

    @Test
    fun `isShotOnTarget expect true when shot is on target`() {
        val shotEventType = mockEventObjects.shotEventType()
        val onTargetOutcome = mockEventObjects.outcome(OutcomeEnum.GOAL.id, "Goal")

        val event = mockEventObjects.shotEvent(shotEventType, onTargetOutcome, 1)

        val isShotOnTarget = service.isShotOnTarget(event)

        assert(isShotOnTarget)
    }

    @Test
    fun `isShotOnTarget expect false when shot is not on target`() {
        val shotEventType = mockEventObjects.shotEventType()
        val waywardOutcome = mockEventObjects.outcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val event = mockEventObjects.shotEvent(shotEventType, waywardOutcome, 1)

        val isShotOnTarget = service.isShotOnTarget(event)

        assert(!isShotOnTarget)
    }

    @Test
    fun `isGoal expect exception when not a shot event`() {
        val event = mockEventObjects.passEvent()

        assertThrows<UnexpectedEventType> {
            service.isGoal(event)
        }
    }

    @Test
    fun `isGoal expect true when shot has a goal outcome`() {
        val shotEventType = mockEventObjects.shotEventType()
        val onTargetOutcome = mockEventObjects.outcome(OutcomeEnum.GOAL.id, "Goal")

        val event = mockEventObjects.shotEvent(shotEventType, onTargetOutcome, 1)

        val isGoal = service.isGoal(event)

        assert(isGoal)
    }

    @Test
    fun `isGoal expect false when shot does not have a goal outcome`() {
        val shotEventType = mockEventObjects.shotEventType()
        val waywardOutcome = mockEventObjects.outcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val event = mockEventObjects.shotEvent(shotEventType, waywardOutcome, 1)

        val isGoal = service.isGoal(event)

        assert(!isGoal)
    }

    @Test
    fun `shotOutcome expect exception when not a shot event`() {
        val event = mockEventObjects.passEvent()

        assertThrows<UnexpectedEventType> {
            service.shotOutcome(event)
        }
    }

    @Test
    fun `shotOutcome expect a valid outcome for a shot`() {
        val shotEventType = mockEventObjects.shotEventType()
        val waywardOutcome = mockEventObjects.outcome(OutcomeEnum.WAYWARD.id, "Wayward")

        val event = mockEventObjects.shotEvent(shotEventType, waywardOutcome, 1)

        val outcome = service.shotOutcome(event)

        assert(outcome.isNotEmpty())
    }
}