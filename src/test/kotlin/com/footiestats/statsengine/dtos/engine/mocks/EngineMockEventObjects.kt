package com.footiestats.statsengine.dtos.engine.mocks

import com.footiestats.statsengine.entities.engine.enums.EventTypeEnum
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.*
import java.time.LocalTime

class EngineMockEventObjects {

    private val mockObjects = EngineMockObjects()

    fun mockEvent() = Event(
            mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
            mockObjects.mockEventType(), 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
            mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", 1)

    fun mockShotEventGoalOutcome(): Event {
        val outcome = mockOutcome(OutcomeEnum.GOAL.id,"Goal")

        return mockShotEvent(mockShotEventType(), outcome, 452)
    }

    fun mockEventType(id: Long, typeName: String) = EventType(
            typeName, mockObjects.mockSource(), "1", id)

    fun mockOutcome(id: Long, outcomeName: String) = Outcome(
            outcomeName, mockObjects.mockSource(), "1", id)

    fun mockShotEvent(eventType: EventType, outcome: Outcome, eventId: Long): Event {
        val event = Event(mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                eventType, 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", eventId)

        val shot = Shot(
                0.5,
                Location3D(1.5, 2.5, 0.5, 1),
                Technique("Volley", mockObjects.mockSource(), "1", 1),
                outcome,
                ShotType("Open Play", mockObjects.mockSource(), "1", 1),
                BodyPart("Right Foot", mockObjects.mockSource(), "1", 1),
                1)
        shot.firstTime = true

        event.shot = shot

        return event
    }

    fun mockShotEventType() = EventType(
            "Shot", mockObjects.mockSource(), "1", EventTypeEnum.SHOT.id)

    fun mockPassEvent(): Event {
        val event = Event(mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockPassEventType(), 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", 78)

        event.player = mockObjects.mockPlayer()

        val pass = Pass(
                12.0, 25.6, PassHeight("High", mockObjects.mockSource(), "1"),
                Location2D(1.5,2.5, 1), 1)

        event.pass = pass

        return event
    }

    fun mockPassEvent(id: Long, outcome: Outcome?, ballReceiptEvent: Event?): Event {
        val event = Event(mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockPassEventType(), 1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", id)

        event.player = mockObjects.mockPlayer()

        val pass = Pass(
                12.0, 25.6, PassHeight("High", mockObjects.mockSource(), "1"),
                Location2D(1.5,2.5, 1), 1)
        pass.outcome = outcome

        event.pass = pass

        if (ballReceiptEvent != null) {
            event.relatedEvents.add(ballReceiptEvent)
        }

        return event
    }

    fun mockBallReceipt(id: Long) = Event(
            mockObjects.mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockEventType(EventTypeEnum.BALL_RECEIPT.id, "Ball Receipt"),
                1, mockObjects.mockTeam1(), mockObjects.mockPlayPattern(),
                mockObjects.mockTeam2(), 1.0, mockObjects.mockSource(), "1", id)

    fun mockPassEventType() = EventType(
            "Pass", mockObjects.mockSource(), "1", EventTypeEnum.PASS.id)

    fun mockTactics() = Tactics(442, 12)

    fun mockTacticalLineupPlayer() = TacticalLineupPlayer(
            17, mockObjects.mockPlayer(), mockPosition(), 1)

    fun mockPosition() = Position("Left Back", mockObjects.mockSource(), "1", 94)

}