package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mappers.exceptions.UnexpectedEventType
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.LineupPlayer
import com.footiestats.statsengine.entities.engine.MatchLineup
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.entities.engine.events.metadata.TacticalLineupPlayer
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassEventOverviewItemMapperTest {

    private val mockObjects = EngineMockObjects()

    private val mockEventObjects = EngineMockEventObjects()

    private var playerMapper = PlayerMapper()

    private var location2DMapper = Location2DMapper()

    @RelaxedMockK
    private lateinit var passAnalysisService: PassAnalysisService

    @InjectMockKs
    private lateinit var mapper: PassEventOverviewItemMapper

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `expect exception when event type is not a Pass`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        assertThrows<UnexpectedEventType> {
            mapper.toDtoFromPassEvent(shotEvent, getLineupPlayer(shotEvent))
        }
    }

    @Test
    fun `expect DTO eventId to match entity eventId`() {
        val passEvent = getPassEvent()

        val result = mapper.toDtoFromPassEvent(passEvent, getLineupPlayer(passEvent))

        assert(result.eventId == passEvent.id)
    }

    @Test
    fun `when event player is called Dave expect DTO player to be called Dave`() {
        val passEvent = getPassEvent()

        val result = mapper.toDtoFromPassEvent(passEvent, getLineupPlayer(passEvent))

        assert(result.player.name == "Dave")
    }

    @Test
    fun `expect event type to be Pass`() {
        val passEvent = getPassEvent()

        val result = mapper.toDtoFromPassEvent(passEvent, getLineupPlayer(passEvent))

        assert(result.type == "Pass")
    }

    @Test
    fun `expect start location on DTO to match Pass event`() {
        val passEvent = getPassEvent()

        val result = mapper.toDtoFromPassEvent(passEvent, getLineupPlayer(passEvent))

        assert(result.startLocation.x == passEvent.location?.x)
        assert(result.startLocation.y == passEvent.location?.y)
    }

    @Test
    fun `expect end location on DTO to match Pass event`() {
        val passEvent = getPassEvent()

        val result = mapper.toDtoFromPassEvent(passEvent, getLineupPlayer(passEvent))

        assert(result.endLocation != null)
        assert(result.endLocation?.x == passEvent.pass?.endLocation?.x)
        assert(result.endLocation?.y == passEvent.pass?.endLocation?.y)
    }

    @Test
    fun `expect `() {

    }

    private fun getPassEvent(): Event {
        val startLocation = Location2D(12.6, 75.8)

        val passEvent = mockEventObjects.passEvent()
        passEvent.location = startLocation
        return passEvent
    }

    private fun getLineupPlayer(event: Event) = TacticalLineupPlayer(
            3,
            event.player!!,
            Position("Central Defender", mockObjects.source(), "1", 45),
            75
    )

}
