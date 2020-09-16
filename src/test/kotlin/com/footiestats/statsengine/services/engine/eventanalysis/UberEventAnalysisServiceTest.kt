package com.footiestats.statsengine.services.engine.eventanalysis

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

// TODO: tests currently failing due to refactoring of EventAnalysisService classes

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UberEventAnalysisServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @SpyK
    private var shotAnalysisService = ShotAnalysisService()

    @SpyK
    private var passAnalysisService = PassAnalysisService()

    @InjectMockKs
    private lateinit var service: UberEventAnalysisService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `isEventSuccessful with goal event expect true`() {
        val event = mockEventObjects.shotEventGoalOutcome()

        val successful = service.isEventSuccessful(event)

        assert(successful!!)
    }

    @Test
    fun `isEventSuccessful with uncollected pass expect false`() {
        val event = mockEventObjects.passEvent()

        val successful = service.isEventSuccessful(event)

        assert(!successful!!)
    }

    @Test
    fun `getOutcome expect a non-empty value for a pass event`() {
        val event = mockEventObjects.passEvent()

        val outcome = service.getOutcome(event)

        assert(outcome.isNotEmpty())
    }

    @Test
    fun `getOutcome expect a non-empty value for a shot event`() {
        val event = mockEventObjects.shotEventGoalOutcome()

        val outcome = service.getOutcome(event)

        assert(outcome.isNotEmpty())
    }

    @Test
    fun `isKeyEvent expect true value when a shot event has a goal outcome`() {
        val event = mockEventObjects.shotEventGoalOutcome()

        val keyEvent = service.isKeyEvent(event)

        assert(keyEvent)
    }

    @Test
    fun `isKeyEvent expect false value when a pass is not received`() {
        val event = mockEventObjects.passEvent()

        val keyEvent = service.isKeyEvent(event)

        assert(!keyEvent)
    }

}
