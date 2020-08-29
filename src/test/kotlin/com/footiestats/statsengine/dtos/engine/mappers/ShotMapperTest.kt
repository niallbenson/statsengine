package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mappers.exceptions.EventHasUnexpectedNullValue
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.services.engine.eventanalysis.EventAnalysisService
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ShotMapperTest {

    private val mockEventObjects = EngineMockEventObjects()

    @MockK
    private var playerMapper = PlayerMapper()

    @MockK
    private var teamMapper = TeamMapper()

    @RelaxedMockK
    private lateinit var eventAnalysisService: EventAnalysisService

    @MockK
    private var eventMapper = EventMapper(playerMapper, teamMapper, eventAnalysisService)

    @InjectMockKs
    private var mapper = ShotMapper(eventMapper, playerMapper)

    @Test
    fun `when entity shot value is null then expect exception`() {
        val nonShotEvent = mockEventObjects.event()

        assertThrows<EventHasUnexpectedNullValue> {
            mapper.toDto(nonShotEvent)
        }
    }

    @Test
    fun `when entity event id is 452 then dto event id should be 452`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.event.id == 452L)
    }

    @Test
    fun `when entity shot type is Open Play then dto shot type should be Open Play`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.type == "Open Play")
    }

    @Test
    fun `when entity shot outcome is Goal then dto shot outcome should be Goal`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.outcome == "Goal")
    }

    @Test
    fun `when entity shot body part is Right Foot then dto shot body part should be Right Foot`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.bodyPart == "Right Foot")
    }

    @Test
    fun `when entity shot technique is Volley then dto shot technique should be Volley`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.technique == "Volley")
    }

    @Test
    fun `when entity shot assist player is Dave then dto shot assist player should be Dave`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()
        shotEvent.shot?.keyPass = mockEventObjects.passEvent()

        val result = mapper.toDto(shotEvent)

        assert(result.keyPassPlayer?.name == "Dave")
    }

    @Test
    fun `when entity shot has no assist player then dto shot assist player should be null`() {
        val shotEvent = mockEventObjects.shotEventGoalOutcome()

        val result = mapper.toDto(shotEvent)

        assert(result.keyPassPlayer == null)
    }

}