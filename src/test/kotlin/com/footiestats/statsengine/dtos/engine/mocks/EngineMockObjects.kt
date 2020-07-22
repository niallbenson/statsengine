package com.footiestats.statsengine.dtos.engine.mocks

import com.footiestats.statsengine.entities.engine.*
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import java.time.LocalDate
import java.time.LocalDateTime

class EngineMockObjects {

    fun match() = Match(
            LocalDateTime.MIN, 0, 1, "available", LocalDateTime.MAX, 1,
            "1", competitionSeason(), team1(), team2(), team1Managers(),
            team2Managers(), matchMetadata(), competitionStage(), stadium(), referee(),
            source(), 1)

    fun competition() = Competition(
            "competition", Gender.MALE, "1", country(), source(), 1)

    fun competition(name: String) = Competition(
            name, Gender.MALE, "1", country(), source(), 1)

    fun country() = Country(
            "England", "1", source(), 1)

    fun source() = Source("source", 1)

    fun season() = Season("season", "1", source(), 1)

    fun competitionSeason() = CompetitionSeason(competition(), season(), 1)

    fun team1() = Team(
            "Ipswich", Gender.MALE, "group", "1", country(),
            team1Managers(), source(), 1)

    fun team1Managers() = mutableListOf(
            Manager("Bob", "Team 1 Manager Nickname", LocalDate.MIN,
                    "1", country(), source()))

    fun team2() = Team(
            "Braintree", Gender.MALE, "group", "1", country(),
            team2Managers(), source(), 2)

    fun team2Managers() = mutableListOf(
            Manager("Clive", "Team 2 Manager Nickname", LocalDate.MIN,
                    "2", country(), source()))

    fun matchMetadata() = MatchMetadata(
            "d", "s", "x", source(), 1)

    fun competitionStage() = CompetitionStage("stage", "1", source(), 1)

    fun stadium() = Stadium("stadium", "1", country(), source(), 1)

    fun referee() = Referee("referee", "1", country(), source(), 1)

    fun eventType() = EventType("Goal", source(), "1", 1)

    fun playPattern() = PlayPattern("pattern", source(), "1", 1)

    fun player() = Player("Dave", "DJ", "1", country(), source(), 1)

}
