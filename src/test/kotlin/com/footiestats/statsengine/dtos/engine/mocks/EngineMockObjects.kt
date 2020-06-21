package com.footiestats.statsengine.dtos.engine.mocks

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import java.time.LocalDate
import java.time.LocalDateTime

class EngineMockObjects {

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

}