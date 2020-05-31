package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEventEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombTeamNotFound
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalTime

private val log = KotlinLogging.logger {}

@Service
class StatsBombEventFeedService(
        private val baseEntityService: StatsBombBaseEntityService,
        private val eventEntityService: StatsBombEventEntityService,
        private val restService: StatsBombRestService) {

    fun run(): Iterable<StatsBombEvent> {
        log.info { "Starting StatsBomb Events Feed" }

        val events = ArrayList<StatsBombEvent>()

        val competitionSeasons = baseEntityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            events.addAll(processCompetitionSeason(competitionSeasons.iterator().next()))
        }

        return events
    }

    private fun processCompetitionSeason(competitionSeason: CompetitionSeason): Iterable<StatsBombEvent> {
        log.info {
            "Importing events for competition=${competitionSeason.competition.name} " +
                    "season=${competitionSeason.season.name}"
        }

        val events = ArrayList<StatsBombEvent>()

        val matches = baseEntityService.getMatchesForCompetitionSeason(competitionSeason)

        for (m in matches.subList(0, 5)) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            events.addAll(restService.getStatsBombEvents(m.sourceExternalId))

            log.info { "${events.size} events found" }
        }

        return events
    }

    private fun mapToEntity(match: Match, statsBombEvent: StatsBombEvent): Event {
        val eventType = eventEntityService.getOrCreateEventType(statsBombEvent.type)

        val possessionTeam = baseEntityService.getTeamByExternalId(statsBombEvent.possessionTeam.id.toString())
                ?: throw StatsBombTeamNotFound("${statsBombEvent.possessionTeam.name} " +
                        "not found - team should already exist before Events are imported")

        val playPattern = eventEntityService.getOrCreatePlayPattern(statsBombEvent.playPattern)

        val eventTeam = baseEntityService.getTeamByExternalId(statsBombEvent.team.id.toString())
                ?: throw StatsBombTeamNotFound("${statsBombEvent.possessionTeam.name} " +
                        "not found - team should already exist before Events are imported")

        val event = Event(
                match,
                statsBombEvent.index,
                statsBombEvent.period,
                LocalTime.parse(statsBombEvent.timestamp),
                statsBombEvent.minute.toInt(),
                statsBombEvent.second.toInt(),
                eventType,
                statsBombEvent.possession,
                possessionTeam,
                playPattern,
                eventTeam,
                statsBombEvent.duration,
                baseEntityService.getStatsBombSource(),
                statsBombEvent.id)

        return event
    }

    private fun addOptionalEventData(event: Event, statsBombEvent: StatsBombEvent) {
        if (statsBombEvent.badBehaviour != null) {
            event.badBehaviour = getBadBehaviour(statsBombEvent.badBehaviour)
        }

        if (statsBombEvent.ballReceipt != null) {
            event.ballReceipt = getBallReceipt(statsBombEvent.ballReceipt)
        }

        if (statsBombEvent.ballRecovery != null) {
            event.ballRecovery = getBallRecovery(statsBombEvent.ballRecovery)
        }

        if (statsBombEvent.carry != null) {
            event.carry = getCarry(statsBombEvent.carry)
        }




//        statsBombEvent.clearance
//        statsBombEvent.deflection
//        statsBombEvent.carry
//        statsBombEvent.counterPress
//        statsBombEvent.dribble
//        statsBombEvent.duel
//        statsBombEvent.fiftyFifty
//        statsBombEvent.foulCommitted
//        statsBombEvent.foulWon
//        statsBombEvent.goalkeeper
//        statsBombEvent.halfEnd
//        statsBombEvent.halfStart
//        statsBombEvent.injuryStoppage
//        statsBombEvent.interception
//        statsBombEvent.miscontrol
//        statsBombEvent.pass
//        statsBombEvent.player
//        statsBombEvent.playerOff
//        statsBombEvent.position
//        statsBombEvent.shot
//        statsBombEvent.substitution
//        statsBombEvent.tactics
    }

    private fun getBadBehaviour(statsBombBadBehaviour: StatsBombBadBehaviour): BadBehaviour {
        val card = eventEntityService.getOrCreateCard(statsBombBadBehaviour.card)

        return BadBehaviour(card)
    }

    private fun getBallReceipt(statsBombBallReceipt: StatsBombBallReceipt): BallReceipt {
        val outcome = eventEntityService.getOrCreateOutcome(statsBombBallReceipt.outcome)

        return BallReceipt(outcome)
    }

    private fun getBallRecovery(statsBombBallRecovery: StatsBombBallRecovery): BallRecovery {
        val ballRecovery = BallRecovery()

        ballRecovery.offensive = statsBombBallRecovery.offensive
        ballRecovery.recoveryFailure = statsBombBallRecovery.recoveryFailure

        return ballRecovery
    }

    private fun getCarry(statsBombCarry: StatsBombCarry): Carry {
        return Carry(
                Location2D(statsBombCarry.endLocation[0], statsBombCarry.endLocation[1])
        )
    }

}