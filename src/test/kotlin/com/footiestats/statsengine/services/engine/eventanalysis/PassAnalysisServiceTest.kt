package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.services.engine.exceptions.UnexpectedEventType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassAnalysisServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @InjectMockKs
    private lateinit var service: PassAnalysisService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `isPassAccurate expect exception when not a pass event`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            service.isAssist(shotEvent)
        }
    }

    @Test
    fun `isPassAccurate expect true for an accurate pass`() {
        val receipt = mockEventObjects.ballReceipt(11)
        val passEvent = mockEventObjects.passEvent(1, null, receipt)

        val isPassAccurate = service.isPassAccurate(passEvent)

        assert(isPassAccurate)
    }

    @Test
    fun `isPassAccurate expect false for an accurate pass`() {
        val incompleteOutcome = mockEventObjects.outcome(OutcomeEnum.INCOMPLETE.id, "Incomplete")
        val passEvent = mockEventObjects.passEvent(1, incompleteOutcome, null)

        val isPassAccurate = service.isPassAccurate(passEvent)

        assert(!isPassAccurate)
    }

    @Test
    fun `passOutcome expect exception when not a pass event`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            service.passOutcome(shotEvent)
        }
    }

    @Test
    fun `passOutcome expect a valid outcome for a pass`() {
        val incompleteOutcome = mockEventObjects.outcome(OutcomeEnum.INCOMPLETE.id, "Incomplete")
        val passEvent = mockEventObjects.passEvent(1, incompleteOutcome, null)

        val outcome = service.passOutcome(passEvent)

        assert(outcome.isNotEmpty())
    }

    @Test
    fun `isAssist expect exception when not a pass event`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            service.isAssist(shotEvent)
        }
    }

    @Test
    fun `isAssist expect true when pass is an assist`() {
        val shotEventType = mockEventObjects.eventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.outcome(OutcomeEnum.GOAL.id, "Goal")
        val goalShot = mockEventObjects.shotEvent(shotEventType, goalOutcome, 725)

        val passEvent = mockEventObjects.passEvent(6, null, null)
        passEvent.pass?.assistedShot = goalShot

        val isAssist = service.isAssist(passEvent)

        assert(isAssist)
    }

    @Test
    fun `isAssist expect false when pass is not an assist`() {
        val passEvent = mockEventObjects.passEvent(6, null, null)

        val isAssist = service.isAssist(passEvent)

        assert(!isAssist)
    }

    @Test
    fun `isKeyPass expect exception when not a pass event`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            service.isKeyPass(shotEvent)
        }
    }

    @Test
    fun `isKeyPass expect true when pass is a key pass`() {
        val shotEventType = mockEventObjects.eventType(EventTypeEnum.SHOT.id, "Shot")
        val goalOutcome = mockEventObjects.outcome(OutcomeEnum.GOAL.id, "Goal")
        val goalShot = mockEventObjects.shotEvent(shotEventType, goalOutcome, 725)

        val passEvent = mockEventObjects.passEvent(6, null, null)
        passEvent.pass?.assistedShot = goalShot

        val isAssist = service.isKeyPass(passEvent)

        assert(isAssist)
    }

    @Test
    fun `isKeyPass expect false when pass is not a key pass`() {
        val passEvent = mockEventObjects.passEvent(6, null, null)

        val isAssist = service.isAssist(passEvent)

        assert(!isAssist)
    }

}