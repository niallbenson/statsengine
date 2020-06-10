package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EventServiceTest {

    private val mockObjects = EngineMockObjects()

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

        every { eventRepository.findAllByPlayer_IdAndMatch_Id(playerId, matchId) } returns
                arrayListOf(mockObjects.mockEvent())

        val result = service.getPlayerMatchEvents(playerId, matchId)

        assert(result.size == 1)
    }

}