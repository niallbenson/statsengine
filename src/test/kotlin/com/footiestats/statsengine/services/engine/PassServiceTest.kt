package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
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
import org.springframework.data.repository.findByIdOrNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassServiceTest {

    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var service: PassService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getPassEventOverviewDto expect exception when eventId not greater than zero`() {
        val eventId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPassEventOverviewDto(eventId)
        }
    }

    @Test
    fun `when eventId is 7 expect result eventId to be 7`() {
        val eventId = 7L

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.eventId == eventId)
    }

    @Test
    fun `expect results previous event to have same eventId`() {
        val eventId = 1L

        val ballReceipt = mockEventObjects.ballReceipt(17)

        val pass = mockEventObjects.passEvent()
        pass.relatedEvents.add(ballReceipt)

        every { eventRepository.findByIdOrNull(eventId) } returns pass

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.previousEvent.eventId == 17L)
    }

}
