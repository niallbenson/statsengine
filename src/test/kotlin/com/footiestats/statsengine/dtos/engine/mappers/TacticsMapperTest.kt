package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.services.engine.eventanalysis.UberEventAnalysisService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TacticsMapperTest {

    private val mockEventObjects = EngineMockEventObjects()

    @MockK
    private var tacticalLineupPlayerMapper = TacticalLineupPlayerMapper()

    @MockK
    private var playerMapper = PlayerMapper()

    @MockK
    private var teamMapper = TeamMapper()

    @RelaxedMockK
    private lateinit var eventAnalysisService: UberEventAnalysisService

    @MockK
    private var eventMapper = EventMapper(playerMapper, teamMapper, eventAnalysisService)

    @InjectMockKs
    private var mapper = TacticsMapper(tacticalLineupPlayerMapper, eventMapper)

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `when tactics id is 12 then dto id should be 12`() {
        val tactics = mockEventObjects.tactics()
        tactics.event = mockEventObjects.event()

        val result = mapper.toDto(tactics)

        assert(result.id == 12L)
    }

    @Test
    fun `when tactics formation is 442 then dto formation should be 442`() {
        val tactics = mockEventObjects.tactics()
        tactics.event = mockEventObjects.event()

        val result = mapper.toDto(tactics)

        assert(result.formation == 442)
    }

    @Test
    fun `when tactics has event id 1 then dto event id should be 1`() {
        val tactics = mockEventObjects.tactics()
        tactics.event = mockEventObjects.event()

        val result = mapper.toDto(tactics)

        assert(result.event.id == 1L)
    }

    @Test
    fun `when tactics lineup has 2 players then dto lineup should have 2 players`() {
        val tactics = mockEventObjects.tactics()
        tactics.event = mockEventObjects.event()
        tactics.lineup = mutableSetOf(
                mockEventObjects.tacticalLineupPlayer(), mockEventObjects.tacticalLineupPlayer())

        val result = mapper.toDto(tactics)

        assert(result.players.size == 2)
    }

}
