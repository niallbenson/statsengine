package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mappers.exceptions.EventHasNullWhereValueExpected
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.BodyPart
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import com.footiestats.statsengine.entities.engine.events.refdata.ShotType
import com.footiestats.statsengine.entities.engine.events.refdata.Technique
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalTime

internal class ShotMapperTest {

    private val mockObjects = EngineMockObjects()

    @MockK
    private var playerMapper = PlayerMapper()

    @MockK
    private var teamMapper = TeamMapper()

    @MockK
    private var eventMapper = EventMapper(playerMapper, teamMapper)

    @InjectMockKs
    private var mapper = ShotMapper(eventMapper, playerMapper)

    @Test
    fun `when entity shot value is null then expect exception`() {
        val nonShotEvent = mockObjects.mockEvent()

        assertThrows<EventHasNullWhereValueExpected> {
            mapper.toDto(nonShotEvent)
        }
    }

    @Test
    fun `when entity event id is 1 then dto event id should be 1`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.event.id == 1L)
    }

    @Test
    fun `when entity shot type is Open Play then dto shot type should be Open Play`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.type == "Open Play")
    }

    @Test
    fun `when entity shot outcome is Goal then dto shot outcome should be Goal`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.outcome == "Goal")
    }

    @Test
    fun `when entity shot body part is Right Foot then dto shot body part should be Right Foot`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.bodyPart == "Right Foot")
    }

    @Test
    fun `when entity shot technique is Volley then dto shot technique should be Volley`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.technique == "Volley")
    }

    @Test
    fun `when entity shot assist player is Dave then dto shot assist player should be Dave`() {
        val shotEvent = mockShotEvent()
        shotEvent.shot!!.keyPass = mockPassEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.keyPassPlayer!!.name == "Dave")
    }

    @Test
    fun `when entity shot has no assist player then dto shot assist player should be null`() {
        val shotEvent = mockShotEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.keyPassPlayer == null)
    }

    private fun mockShotEvent(): Event {
        val event = Event(mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockShotEventType(), 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", 1)

        val shot = Shot(
                0.5,
                Location3D(1.5, 2.5, 0.5, 1),
                Technique("Volley", mockObjects.mockSource(), "1", 1),
                Outcome("Goal", mockObjects.mockSource(), "1", 1),
                ShotType("Open Play", mockObjects.mockSource(), "1", 1),
                BodyPart("Right Foot", mockObjects.mockSource(), "1", 1),
                1)
        shot.firstTime = true

        event.shot = shot

        return event
    }

    private fun mockShotEventType() = EventType("Shot", mockObjects.mockSource(), "1", 1)

    private fun mockPassEvent(): Event {
        val event = Event(mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockPassEventType(), 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", 1)

        event.player = mockObjects.mockPlayer()

        val pass = Pass(
                    12.0, 25.6, PassHeight("High", mockObjects.mockSource(), "1"),
                Location2D(1.5,2.5, 1), 1)

        event.pass = pass

        return event
    }

    private fun mockPassEventType() = EventType("Pass", mockObjects.mockSource(), "1", 1)
}