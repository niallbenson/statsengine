package com.footiestats.statsengine.services.engine

import com.footiestats.statsengine.dtos.engine.mappers.Location2DMapper
import com.footiestats.statsengine.dtos.engine.mappers.PassEventOverviewItemMapper
import com.footiestats.statsengine.dtos.engine.mappers.TacticalLineupPlayerMapper
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.Location2D
import com.footiestats.statsengine.repos.engine.EventRepository
import com.footiestats.statsengine.repos.engine.LineupPlayerRepository
import com.footiestats.statsengine.repos.engine.SubstitutionRepository
import com.footiestats.statsengine.services.engine.eventanalysis.PassAnalysisService
import com.footiestats.statsengine.services.engine.exceptions.EntityIdMustBeGreaterThanZero
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassServiceTest {

    private val mockObjects = EngineMockObjects()
    private val mockEventObjects = EngineMockEventObjects()

    @RelaxedMockK
    private lateinit var eventRepository: EventRepository

    @RelaxedMockK
    private lateinit var substitutionRepository: SubstitutionRepository

    @RelaxedMockK
    private lateinit var lineupPlayerRepository: LineupPlayerRepository

    private var passAnalysisService = PassAnalysisService()

    private var passEventOverviewItemMapper = PassEventOverviewItemMapper(
            TacticalLineupPlayerMapper(), Location2DMapper()
    )

    @InjectMockKs
    private lateinit var service: PassService

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `getPassEventOverviewDto expect exception when eventId not greater than zero`() {
        val eventId = -1L

        assertThrows<EntityIdMustBeGreaterThanZero> {
            service.getPassEventOverviewDto(eventId)
        }
    }

    @Test
    fun `when eventId is 7 expect result eventId to be 7`() {
        val match = mockObjects.match()
        val eventIndex = 25
        val eventId = 7L

        val passEvent = getPassEvent(match, eventIndex, eventId)

        every { eventRepository.findByIdOrNull(eventId) } returns passEvent

        every { eventRepository.findByMatch_IdAndEventIndex(match.id!!, eventIndex - 1) } returns
                getPreviousEventAsPass()

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.eventId == eventId)
    }

    @Test
    fun `expect DTO previous event to have same eventId as entity previous event`() {
        val match = mockObjects.match()
        val eventIndex = 25
        val eventId = 7L

        val passEvent = getPassEvent(match, eventIndex, eventId)

        every { eventRepository.findByIdOrNull(eventId) } returns passEvent

        every { eventRepository.findByMatch_IdAndEventIndex(match.id!!, eventIndex - 1) } returns
                getPreviousEventAsPass()

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.previousEvent.eventId == 78L)
    }

    @Test
    fun `when previous event is a ball receipt then expect DTO to have related pass event to ball receipt`() {
        val match = mockObjects.match()
        val eventIndex = 25
        val eventId = 7L

        val passEvent = getPassEvent(match, eventIndex, eventId)

        every { eventRepository.findByIdOrNull(eventId) } returns passEvent

        val ballReceiptEvent = getPreviousEventAsBallReceipt()

        every { eventRepository.findByMatch_IdAndEventIndex(match.id!!, eventIndex - 1) } returns
                ballReceiptEvent

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.previousEvent.eventId == 78L)
    }

    @Test
    fun `expect DTO pass event to have same eventId as entity event`() {
        val match = mockObjects.match()
        val eventIndex = 25
        val eventId = 7L

        val passEvent = getPassEvent(match, eventIndex, eventId)

        every { eventRepository.findByIdOrNull(eventId) } returns passEvent

        every { eventRepository.findByMatch_IdAndEventIndex(match.id!!, eventIndex - 1) } returns
                getPreviousEventAsPass()

        val result = service.getPassEventOverviewDto(eventId)

        assert(result.pass.eventId == eventId)
    }

    @Test
    fun `expect DTO target event to have same eventId as entity target event`() {
        val match = mockObjects.match()
        val eventIndex = 25
        val eventId = 7L

        val passEvent = getPassEvent(match, eventIndex, eventId)

        every { eventRepository.findByIdOrNull(eventId) } returns passEvent

        val previousPassEvent = getPreviousEventAsPass()
        every { eventRepository.findByMatch_IdAndEventIndex(match.id!!, eventIndex - 1) } returns
                previousPassEvent

        every {
            eventRepository.getTacticalLineupPlayerAtEventIndex(
                    match.id!!, passEvent.player?.id!!, eventIndex,
                    EventTypeEnum.STARTING_XI.id, EventTypeEnum.TACTICAL_SHIFT.id,
                    PageRequest.of(0, 1))
        } returns arrayListOf(
                mockEventObjects.tacticalLineupPlayer()
        )

        every {
            eventRepository.getTacticalLineupPlayerAtEventIndex(
                    match.id!!, passEvent.player?.id!!, previousPassEvent.eventIndex,
                    EventTypeEnum.STARTING_XI.id, EventTypeEnum.TACTICAL_SHIFT.id,
                    PageRequest.of(0, 1))
        } returns arrayListOf(
                mockEventObjects.tacticalLineupPlayer()
        )

        val result = service.getPassEventOverviewDto(eventId)

        val ballReceiptEvent = passEvent.relatedEvents.find { it.type.id == EventTypeEnum.BALL_RECEIPT.id }

        assert(result.target != null)
        assert(result.target?.eventId == ballReceiptEvent?.id)
    }

    private fun getPassEvent(match: Match, eventIndex: Int, eventId: Long): Event {
        val event = Event(match, eventIndex, 1, LocalTime.NOON, 5, 16,
                mockEventObjects.passEventType(), 4, mockObjects.team1(), mockObjects.playPattern(),
                mockObjects.team1(), 1.56, mockObjects.source(), "1", eventId)

        event.location = Location2D(2.5, 35.8)
        event.player = mockObjects.player()
        event.pass = mockEventObjects.pass()
        event.relatedEvents.add(mockEventObjects.ballReceipt(65))

        return event
    }

    private fun getPreviousEventAsPass(): Event {
        val event = mockEventObjects.passEvent()
        event.location = Location2D(2.3, 45.8)

        return event
    }

    private fun getPreviousEventAsBallReceipt(): Event {
        val event = mockEventObjects.ballReceipt(12)

        event.player = mockObjects.player()

        return event
    }

}
