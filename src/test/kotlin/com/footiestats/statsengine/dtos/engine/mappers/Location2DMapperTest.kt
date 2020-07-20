package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Location2DMapperTest {

    private val mockEventObjects = EngineMockEventObjects()

    @InjectMockKs
    private var mapper = Location2DMapper()

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `expect entity location X to match DTO location X`() {
        val entity = mockEventObjects.location2D()

        val result = mapper.toDto(entity)

        assert(result.x == entity.x)
    }

    @Test
    fun `expect entity location Y to match DTO location Y`() {
        val entity = mockEventObjects.location2D()

        val result = mapper.toDto(entity)

        assert(result.y == entity.y)
    }

}
