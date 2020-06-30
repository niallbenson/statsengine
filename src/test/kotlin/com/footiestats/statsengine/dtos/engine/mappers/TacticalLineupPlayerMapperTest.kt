package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TacticalLineupPlayerMapperTest {

    private val mockEventObjects = EngineMockEventObjects()

    @InjectMockKs
    private var mapper = TacticalLineupPlayerMapper()

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `when entity id is 1 then dto id should be 1`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.id == 1L)
    }

    @Test
    fun `when entity player name is Dave then dto player name should be Dave`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.name == "Dave")
    }

    @Test
    fun `when entity player nickName is DJ then dto nickName should be DJ`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.nickName == "DJ")
    }

    @Test
    fun `when entity player id is 1 then dto playerId should be 1`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.playerId == 1L)
    }

    @Test
    fun `when entity jerseyNumber is 17 then dto jerseyNumber should be 17`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.jerseyNumber == 17)
    }

    @Test
    fun `when entity position id is 94 then dto positionId should be 94`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.positionId == 94L)
    }

    @Test
    fun `when entity position name is Left Back then dto position should be Left Back`() {
        val tacticalLineupPlayer = mockEventObjects.tacticalLineupPlayer()

        val result = mapper.toDto(tacticalLineupPlayer)

        assert(result.position == "Left Back")
    }

}