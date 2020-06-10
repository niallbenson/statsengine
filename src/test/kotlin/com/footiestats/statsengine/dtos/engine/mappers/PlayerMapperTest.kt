package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PlayerMapperTest {

    private val mockObjects = EngineMockObjects()

    @InjectMockKs
    private var mapper = PlayerMapper()

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `when entity playerId is 1 then dto id should be 1`() {
        val player = mockObjects.mockPlayer()

        val result = mapper.toDto(player)

        assert(result.id == 1L)
    }

    @Test
    fun `when entity name is Dave then dto name should be Dave`() {
        val player = mockObjects.mockPlayer()

        val result = mapper.toDto(player)

        assert(result.name == "Dave")
    }

    @Test
    fun `when entity nickName is DJ then dto name should be nickName`() {
        val player = mockObjects.mockPlayer()

        val result = mapper.toDto(player)

        assert(result.nickName == "DJ")
    }

    @Test
    fun `when entity country is England then dto country should be England`() {
        val player = mockObjects.mockPlayer()

        val result = mapper.toDto(player)

        assert(result.country == "England")
    }
}