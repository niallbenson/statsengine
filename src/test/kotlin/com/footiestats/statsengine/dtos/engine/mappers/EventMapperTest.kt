package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EventMapperTest {

    private val mockEventObjects = EngineMockEventObjects()
    private val mockObjects = EngineMockObjects()

    @MockK
    private var playerMapper = PlayerMapper()

    @MockK
    private var teamMapper = TeamMapper()

    @InjectMockKs
    private var mapper = EventMapper(playerMapper, teamMapper)

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `when entity event id is 1 then dto id should be 1`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.id == 1L)
    }

    @Test
    fun `when entity lineupPlayer name is Dave then dto player name should be Dave`() {
        val eventEntity = mockEventObjects.event()
        eventEntity.player = mockObjects.player()

        val result = mapper.toDto(eventEntity)

        assert(result.player!!.name == "Dave")
    }

    @Test
    fun `when entity event type is goal dto then event type should be goal`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.eventType == "Goal")
    }

    @Test
    fun `when entity period is 2 then dto period should be 2`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.period == 2)
    }

    @Test
    fun `when entity minute is 12 then dto minute should be 12`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.minute == 12)
    }

    @Test
    fun `when entity second is 45 then dto second should be 45`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.second == 45)
    }

    @Test
    fun `when entity event team is Braintree then dto event team should be Braintree`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.eventTeam.name == "Braintree")
    }

    @Test
    fun `when entity possession team is Ipswich then dto possession team should be Ipswich`() {
        val eventEntity = mockEventObjects.event()

        val result = mapper.toDto(eventEntity)

        assert(result.possessionTeam.name == "Ipswich")
    }
}
