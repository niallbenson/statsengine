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

    fun event() = Event(
            mockObjects.match(), 99, 2, LocalTime.NOON, 12, 45,
            mockObjects.eventType(), 1, mockObjects.team1(), mockObjects.playPattern(),
            mockObjects.team2(), 1.0, mockObjects.source(), "1", 1)

    fun shotEventGoalOutcome(): Event {
        val outcome = outcome(OutcomeEnum.GOAL.id,"Goal")

        return shotEvent(shotEventType(), outcome, 452)
    }

    fun eventType(id: Long, typeName: String) = EventType(
            typeName, mockObjects.source(), "1", id)

    fun outcome(id: Long, outcomeName: String) = Outcome(
            outcomeName, mockObjects.source(), "1", id)

    fun shotEvent(eventType: EventType, outcome: Outcome, eventId: Long): Event {
        val event = Event(mockObjects.match(), 99, 2, LocalTime.NOON, 12, 45,
                eventType, 1, mockObjects.team1(), mockObjects.playPattern(),
                mockObjects.team2(), 1.0, mockObjects.source(), "1", eventId)
        event.player = mockObjects.player()

        val shot = Shot(
                0.5,
                Location3D(1.5, 2.5, 0.5, 1),
                Technique("Volley", mockObjects.source(), "1", 1),
                outcome,
                ShotType("Open Play", mockObjects.source(), "1", 1),
                BodyPart("Right Foot", mockObjects.source(), "1", 1),
                1)
        shot.firstTime = true

        event.shot = shot

        return event
    }

    fun shotEventType() = EventType(
            "Shot", mockObjects.source(), "1", EventTypeEnum.SHOT.id)

    fun passEvent(): Event {
        val event = Event(mockObjects.match(), 99, 2, LocalTime.NOON, 12, 45,
                passEventType(), 1, mockObjects.team1(), mockObjects.playPattern(),
                mockObjects.team2(), 1.0, mockObjects.source(), "1", 78)

        event.player = mockObjects.player()

        val pass = pass()

        event.pass = pass

        return event
    }

    fun passEventWithIndex(id: Long, outcome: Outcome?, ballReceiptEvent: Event?, index: Int): Event {
        val event = Event(mockObjects.match(), index, 2, LocalTime.NOON, 12, 45,
                passEventType(), 1, mockObjects.team1(), mockObjects.playPattern(),
                mockObjects.team2(), 1.0, mockObjects.source(), "1", id)

        event.player = mockObjects.player()

        val pass = pass()
        pass.outcome = outcome

        event.pass = pass

        if (ballReceiptEvent != null) {
            event.relatedEvents.add(ballReceiptEvent)
        }

        return event
    }

    fun passEvent(id: Long, outcome: Outcome?, ballReceiptEvent: Event?) =
            passEventWithIndex(id, outcome, ballReceiptEvent, 99)

    fun pass() = Pass(
                12.0, 25.6, PassHeight("High", mockObjects.source(), "1"),
                Location2D(1.5, 2.5, 1), 1)

    fun ballReceipt(id: Long): Event {
        val event = Event(mockObjects.match(), 99, 2, LocalTime.NOON, 12, 45,
                eventType(EventTypeEnum.BALL_RECEIPT.id, "Ball Receipt"),
                1, mockObjects.team1(), mockObjects.playPattern(),
                mockObjects.team2(), 1.0, mockObjects.source(), "1", id)

        event.player = mockObjects.player()
        event.location = Location2D(26.5, 75.6)

        return event
    }

    fun passEventType() = EventType(
            "Pass", mockObjects.source(), "1", EventTypeEnum.PASS.id)

    fun tactics() = Tactics(442, 12)

    fun tacticalLineupPlayer() = TacticalLineupPlayer(
            17, mockObjects.player(), position(), 1)

    fun position() = Position("Left Back", mockObjects.source(), "1", 94)

    fun location2D() = Location2D(15.6, 87.8)

    fun tacticalLineupPlayer(event: Event) = TacticalLineupPlayer(
            3,
            event.player!!,
            Position("Central Defender", mockObjects.source(), "1", 45),
            75
    )

}
