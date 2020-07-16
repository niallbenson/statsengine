package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mappers.exceptions.UnexpectedEventType
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassEventOverviewItemMapperTest {

    private val mockEventObjects = EngineMockEventObjects()

    @SpyK
    private var playerMapper = PlayerMapper()

    @InjectMockKs
    private var mapper = PassEventOverviewItemMapper(playerMapper)

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `expect exception when event type is not a Pass`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            mapper.toDto(shotEvent)
        }
    }

    @Test
    fun `when eventId is 7 expect DTO eventId to be 7`() {
        val passEvent = mockEventObjects.passEvent(7, null, null)

        val result = mapper.toDto(passEvent)

        assert(result.eventId == 7L)
    }

    @Test
    fun `when event player is called Dave expect DTO player to be called Dave`() {
        val passEvent = mockEventObjects.passEvent()

        val result = mapper.toDto(passEvent)

        assert(result.player?.name == "Dave")
    }

}
