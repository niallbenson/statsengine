package com.footiestats.statsengine.services.feed.statsbomb.feeds.events

import com.footiestats.statsengine.dtos.engine.mocks.EngineMockEventObjects
import com.footiestats.statsengine.dtos.engine.mocks.EngineMockObjects
import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.dtos.statsbomb.mappers.StatsBombEventMapper
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.PassHeight
import com.footiestats.statsengine.entities.engine.events.refdata.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEventEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.utils.StatsBombFileUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StatsBombEventMatchAsyncTest {

    private val eventDataJsonPath =
            "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/"

    private val mockObjects = EngineMockObjects()
    private val mockEventObjects = EngineMockEventObjects()

    @MockK
    private lateinit var restService: StatsBombRestService

    @MockK
    private lateinit var eventEntityService: StatsBombEventEntityService

    @MockK
    private lateinit var baseEntityService: StatsBombBaseEntityService

    @InjectMockKs
    private lateinit var service: StatsBombEventMatchAsync

    @BeforeAll
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `test bad behaviour event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "bad-behaviour.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(24, "Bad Behaviour"))
        } returns EventType("Bad Behaviour", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("18147")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(11, "Left Defensive Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(3, "From Free Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateCard(StatsBombCard(7, "Yellow Card"))
        } returns Card("Yellow Card", mockObjects.source(), "7")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `ball receipt event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "ball-receipt.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(42, "Ball Receipt*"))
        } returns EventType("Ball Receipt*", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("15725")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(2, "Right Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(9, "From Kick Off"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(9, "Incomplete"))
        } returns mockEventObjects.outcome(45, "Incomplete")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `ball recovery event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "ball-recovery.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(2, "Ball Recovery"))
        } returns EventType("Ball Recovery", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("10251")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(19, "Center Attacking Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(3, "From Free Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `carry event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "carry.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(43, "Carry"))
        } returns EventType("Carry", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("15577")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(15, "Left Center Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `clearance event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "clearance.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(9, "Clearance"))
        } returns EventType("Clearance", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31553")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(2, "Right Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(3, "From Free Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateBodyPart(StatsBombBodyPart(37, "Head"))
        } returns BodyPart("Head", mockObjects.source(), "1")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `block event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "block.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(6, "Block"))
        } returns EventType("Block", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31553")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(2, "Right Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `dribble event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "dribble.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(14, "Dribble"))
        } returns EventType("Dribble", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("26570")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(24, "Left Center Forward"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(6, "From Counter"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(9, "Incomplete"))
        } returns mockEventObjects.outcome(45, "Incomplete")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `duel event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "duel.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(4, "Duel"))
        } returns EventType("Duel", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31628")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(5, "Left Center Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateDuelType(StatsBombDuelType(10, "Aerial Lost"))
        } returns DuelType("Aerial Lost", mockObjects.source(), "1")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `fifty fifty event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "50-50.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(33, "50/50"))
        } returns EventType("50/50", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("10190")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(10, "Center Defensive Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(2, "From Corner"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(2, "Success To Opposition"))
        } returns mockEventObjects.outcome(45, "Success To Opposition")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `foul committed event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "foul-committed.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(22, "Foul Committed"))
        } returns EventType("Foul Committed", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31556")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(6, "Left Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(2, "From Corner"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateFoulCommittedType(StatsBombFoulCommittedType(23, "Foul Out"))
        } returns FoulCommittedType("Foul Out", mockObjects.source(), "1")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `foul won event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "foul-won.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(21, "Foul Won"))
        } returns EventType("Foul Won", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31558")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(23, "Center Forward"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(7, "From Goal Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `goalkeeper event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "goalkeeper.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(23, "Goal Keeper"))
        } returns EventType("Goal Keeper", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("22027")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(1, "Goalkeeper"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(59, "Touched Out"))
        } returns mockEventObjects.outcome(45, "Touched Out")

        every {
            eventEntityService.getOrCreateGoalkeeperType(StatsBombGoalkeeperType(33, "Shot Saved"))
        } returns GoalkeeperType("Shot Saved", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreateBodyPart(StatsBombBodyPart(38, "Left Foot"))
        } returns BodyPart("Left Foot", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(44, "Set"))
        } returns Position("Set", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreateTechnique(StatsBombTechnique(46, "Standing"))
        } returns Technique("Standing", mockObjects.source(), "1")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `half end event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "half-end.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(34, "Half End"))
        } returns EventType("Half End", mockObjects.source(), "1")

        every {
            baseEntityService.getTeamByExternalId("966")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `half start event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "half-start.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(18, "Half Start"))
        } returns EventType("Half Start", mockObjects.source(), "1")

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `injury stoppage event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "injury-stoppage.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(40, "Injury Stoppage"))
        } returns EventType("Injury Stoppage", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("10190")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(10, "Center Defensive Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `interception event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "interception.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(10, "Interception"))
        } returns EventType("Interception", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("19780")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(6, "Left Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(4, "Won"))
        } returns mockEventObjects.outcome(45, "Incomplete")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `miscontrol event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "miscontrol.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(38, "Miscontrol"))
        } returns EventType("Miscontrol", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31558")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(23, "Center Forward"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(3, "From Free Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `pass event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "pass.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(30, "Pass"))
        } returns EventType("Pass", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("22027")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(1, "Goalkeeper"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(3, "From Free Kick"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateBodyPart(StatsBombBodyPart(40,"Right Foot"))
        } returns BodyPart("Right Foot", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreatePassType(StatsBombPassType(62, "Free Kick"))
        } returns PassType("Free Kick", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreatePassHeight(StatsBombPassHeight(3, "High Pass"))
        } returns PassHeight("High Pass", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("31558")
        } returns mockObjects.player()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `player off event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "player-off.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(27, "Player Off"))
        } returns EventType("Player Off", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("15575")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(3, "Right Center Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("967")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `shot event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "shot.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(16, "Shot"))
        } returns EventType("Shot", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("15573")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(11, "Left Defensive Midfield"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("967")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateTechnique(StatsBombTechnique(93, "Normal"))
        } returns Technique("Normal", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(98, "Off T"))
        } returns Outcome("Off T", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreateShotType(StatsBombShotType(87, "Open Play"))
        } returns ShotType("Open Plan", mockObjects.source(), "1")

        every {
            eventEntityService.getOrCreateBodyPart(StatsBombBodyPart(40, "Right Foot"))
        } returns BodyPart("Right Foot", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("18253")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(20, "Left Attacking Midfield"))
        } returns Position("Left Attacking Midfield", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("15577")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(18, "Right Attacking Midfield"))
        } returns Position("Right Attacking Midfield", mockObjects.source(), "1")

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `substitution event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "substitution.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(19, "Substitution"))
        } returns EventType("Substitution", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("10198")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(3, "Right Center Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            baseEntityService.getTeamByExternalId("972")
        } returns mockObjects.team2()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(4, "From Throw In"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.getOrCreateOutcome(StatsBombOutcome(102, "Injury"))
        } returns mockEventObjects.outcome(45, "Injury")

        every {
            baseEntityService.getPlayerByExternalId("21021")
        } returns mockObjects.player()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }

    @Test
    fun `starting XI event type`() {
        val match = mockObjects.match()

        val json = StatsBombFileUtils.readFile(
                "src/test/kotlin/com/footiestats/statsengine/services/feed/statsbomb/feeds/events/jsons/" +
                        "starting-xi.json")

        every {
            baseEntityService.getStatsBombSource()
        } returns mockObjects.source()

        every {
            restService.getStatsBombEvents(match.sourceExternalId)
        } returns StatsBombEventMapper.fromJson(json)

        every {
            eventEntityService.getOrCreateEventType(StatsBombEventType(35, "Starting XI"))
        } returns EventType("Starting XI", mockObjects.source(), "1")

        every {
            baseEntityService.getPlayerByExternalId("33664")
        } returns mockObjects.player()

        every {
            baseEntityService.getPlayerByExternalId("15725")
        } returns mockObjects.player()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(1, "Goalkeeper"))
        } returns mockEventObjects.position()

        every {
            eventEntityService.getOrCreatePosition(StatsBombPosition(2, "Right Back"))
        } returns mockEventObjects.position()

        every {
            baseEntityService.getTeamByExternalId("974")
        } returns mockObjects.team1()

        every {
            eventEntityService.getOrCreatePlayPattern(StatsBombPlayPattern(1, "Regular Play"))
        } returns mockObjects.playPattern()

        every {
            eventEntityService.saveAll(any())
        } returns mutableSetOf()

        val result = service.processMatch(match).get()

        assert(result.isNotEmpty())
    }
}
