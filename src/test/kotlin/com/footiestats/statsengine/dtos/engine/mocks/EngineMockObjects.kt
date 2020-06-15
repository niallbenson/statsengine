package com.footiestats.statsengine.dtos.engine.mocks

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EngineMockObjects {

    fun mockEvent() = Event(
            mockMatch(), 99, 2, LocalTime.NOON, 12, 45, mockEventType(), 1,
            mockTeam1(), mockPlayPattern(), mockTeam2(), 1.0, mockSource(), "1", 1)

    fun mockMatch() = Match(
            LocalDateTime.MIN, 0, 1, "available", LocalDateTime.MAX, 1,
            "1", mockCompetitionSeason(), mockTeam1(), mockTeam2(), mockTeam1Managers(),
            mockTeam2Managers(), mockMatchMetadata(), mockCompetitionStage(), mockStadium(), mockReferee(),
            mockSource(), 1)

    fun mockCompetition() = Competition(
            "competition", Gender.MALE, "1", mockCountry(), mockSource(), 1)

    fun mockCountry() = Country(
            "England", "1", mockSource(), 1)

    fun mockSource() = Source("source", 1)

    fun mockSeason() = Season("season", "1", mockSource(), 1)

    fun mockCompetitionSeason() = CompetitionSeason(mockCompetition(), mockSeason(), 1)

    fun mockTeam1() = Team(
            "Ipswich", Gender.MALE, "group", "1", mockCountry(),
            mockTeam1Managers(), mockSource(), 1)

    fun mockTeam1Managers() = mutableListOf(
            Manager("Bob", "Team 1 Manager Nickname", LocalDate.MIN,
                    "1", mockCountry(), mockSource()))

    fun mockTeam2() = Team(
            "Braintree", Gender.MALE, "group", "1", mockCountry(),
            mockTeam2Managers(), mockSource(), 2)

    fun mockTeam2Managers() = mutableListOf(
            Manager("Clive", "Team 2 Manager Nickname", LocalDate.MIN,
                    "2", mockCountry(), mockSource()))

    fun mockMatchMetadata() = MatchMetadata(
            "d", "s", "x", mockSource(), 1)

    fun mockCompetitionStage() = CompetitionStage("stage", "1", mockSource(), 1)

    fun mockStadium() = Stadium("stadium", "1", mockCountry(), mockSource(), 1)

    fun mockReferee() = Referee("referee", "1", mockCountry(), mockSource(), 1)

    fun mockEventType() = EventType("Goal", mockSource(), "1", 1)

    fun mockPlayPattern() = PlayPattern("pattern", mockSource(), "1", 1)

    fun mockLineupPlayer() = LineupPlayer(1, mockPlayer(), mockMatchLineup(), 1)

    fun mockPlayer() = Player("Dave", "DJ", "1", mockCountry(), mockSource(), 1)

    fun mockMatchLineup() = MatchLineup(mockMatch(), mockTeam1(), 1)

    fun mockShotEvent(): Event {
        val event = Event(mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockShotEventType(), 1, mockTeam1(), mockPlayPattern(),
                mockTeam2(), 1.0, mockSource(), "1", 1)

        val shot = Shot(
                0.5,
                Location3D(1.5, 2.5, 0.5, 1),
                Technique("Volley", mockSource(), "1", 1),
                Outcome("Goal", mockSource(), "1", 1),
                ShotType("Open Play", mockSource(), "1", 1),
                BodyPart("Right Foot", mockSource(), "1", 1),
                1)
        shot.firstTime = true

        event.shot = shot

        return event
    }

    fun mockShotEventType() = EventType("Shot", mockSource(), "1", 1)

    fun mockPassEvent(): Event {
        val event = Event(mockMatch(), 99, 2, LocalTime.NOON, 12, 45,
                mockPassEventType(), 1, mockTeam1(), mockPlayPattern(),
                mockTeam2(), 1.0, mockSource(), "1", 1)

        event.player = mockPlayer()

        val pass = Pass(
                12.0, 25.6, PassHeight("High", mockSource(), "1"),
                Location2D(1.5,2.5, 1), 1)

        event.pass = pass

        return event
    }

    fun mockPassEventType() = EventType("Pass", mockSource(), "1", 1)
    
}