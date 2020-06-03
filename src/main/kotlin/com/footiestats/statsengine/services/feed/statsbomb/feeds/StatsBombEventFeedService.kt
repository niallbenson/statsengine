package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombBaseEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEventEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEventNotFound
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombPlayerNotFound
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

    fun run() {
        log.info { "Starting StatsBomb Events Feed" }

        val competitionSeasons = baseEntityService.getCompetitionSeasons()

        for (cs in competitionSeasons) {
            processCompetitionSeason(cs)
        }
    }

    private fun processCompetitionSeason(competitionSeason: CompetitionSeason) {
        log.info {
            "Importing events for competition=${competitionSeason.competition.name} " +
                    "season=${competitionSeason.season.name}"
        }

        val matches = baseEntityService.getMatchesForCompetitionSeason(competitionSeason)

        for (m in matches) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            val statsBombEvents = restService.getStatsBombEvents(m.sourceExternalId)

            val eventEntities = HashMap<String, Event>()

            statsBombEvents.forEach {
                eventEntities[it.id] = mapToEntity(m, it)
            }

            setMatchRelatedEvents(eventEntities, statsBombEvents)
        }
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

        setOptionalEventData(event, statsBombEvent)
        setOptionalFlags(event, statsBombEvent)

        eventEntityService.save(event)

        return event
    }

    private fun setOptionalEventData(event: Event, statsBombEvent: StatsBombEvent) {
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

        if (statsBombEvent.clearance != null) {
            event.clearance = getClearance(statsBombEvent.clearance)
        }

        if (statsBombEvent.block != null) {
            event.block = getBlock(statsBombEvent.block)
        }

        if (statsBombEvent.carry != null) {
            event.carry = getCarry(statsBombEvent.carry)
        }

        if (statsBombEvent.dribble != null) {
            event.dribble = getDribble(statsBombEvent.dribble)
        }

        if (statsBombEvent.duel != null) {
            event.duel = getDuel(statsBombEvent.duel)
        }

        if (statsBombEvent.fiftyFifty != null) {
            event.fiftyFifty = getFiftyFifty(statsBombEvent.fiftyFifty)
        }

        if (statsBombEvent.foulCommitted != null) {
            event.foulCommitted = getFoulCommitted(statsBombEvent.foulCommitted)
        }

        if (statsBombEvent.foulWon != null) {
            event.foulWon = getFoulWon(statsBombEvent.foulWon)
        }

        if (statsBombEvent.goalkeeper != null) {
            event.goalKeeper = getGoalKeeper(statsBombEvent.goalkeeper)
        }

        if (statsBombEvent.halfEnd != null) {
            event.halfEnd = getHalfEnd(statsBombEvent.halfEnd)
        }

        if (statsBombEvent.halfStart != null) {
            event.halfStart = getHalfStart(statsBombEvent.halfStart)
        }

        if (statsBombEvent.injuryStoppage != null) {
            event.injuryStoppage = getInjuryStoppage(statsBombEvent.injuryStoppage)
        }

        if (statsBombEvent.interception != null) {
            event.interception = getInterception(statsBombEvent.interception)
        }

        if (statsBombEvent.miscontrol != null) {
            event.miscontrol = getMiscontrol(statsBombEvent.miscontrol)
        }

        if (statsBombEvent.pass != null) {
            event.pass = getPass(statsBombEvent.pass)
        }

        if (statsBombEvent.player != null) {
            event.player = getPlayer(statsBombEvent.player)
        }

        if (statsBombEvent.playerOff != null) {
            event.playerOff = getPlayerOff(statsBombEvent.playerOff)
        }

        if (statsBombEvent.position != null) {
            event.position = getPosition(statsBombEvent.position)
        }

        if (statsBombEvent.shot != null) {
            event.shot = getShot(statsBombEvent.shot)
        }

        if (statsBombEvent.substitution != null) {
            event.substitution = getSubstitution(statsBombEvent.substitution)
        }

        if (statsBombEvent.tactics != null) {
            event.tactics = getTactics(statsBombEvent.tactics)
        }

        if (statsBombEvent.location != null) {
            event.location = getLocation(statsBombEvent.location)
        }

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

    private fun getClearance(statsBombClearance: StatsBombClearance): Clearance {
        val clearance = Clearance()

        clearance.aerialWon = statsBombClearance.aerialWon
        clearance.head = statsBombClearance.head
        clearance.leftFoot = statsBombClearance.leftFoot
        clearance.rightFoot = statsBombClearance.rightFoot
        clearance.other = statsBombClearance.other

        if (statsBombClearance.bodyPart != null) {
            clearance.bodyPart = eventEntityService.getOrCreateBodyPart(statsBombClearance.bodyPart)
        }
        return clearance
    }

    private fun getBlock(statsBombBlock: StatsBombBlock): Block {
        val block = Block()

        block.deflection = statsBombBlock.deflection
        block.offensive = statsBombBlock.offensive
        block.saveBlock = statsBombBlock.saveBock

        return block
    }

    private fun getDribble(statsBombDribble: StatsBombDribble): Dribble {
        val dribble = Dribble(
                eventEntityService.getOrCreateOutcome(statsBombDribble.outcome),
                statsBombDribble.overrun
        )
        dribble.noTouch = statsBombDribble.noTouch
        dribble.nutmeg = statsBombDribble.nutmeg

        return dribble
    }

    private fun getDuel(statsBombDuel: StatsBombDuel): Duel {
        val duel = Duel(
                eventEntityService.getOrCreateEventType(statsBombDuel.type)
        )

        if (statsBombDuel.outcome != null) {
            duel.outcome = eventEntityService.getOrCreateOutcome(statsBombDuel.outcome)
        }
        return duel
    }

    private fun getFiftyFifty(statsBombFiftyFifty: StatsBombFiftyFifty): FiftyFifty {
        val outcome = eventEntityService.getOrCreateOutcome(statsBombFiftyFifty.outcome)

        return FiftyFifty(outcome)
    }

    private fun getFoulCommitted(statsBombFoulCommitted: StatsBombFoulCommitted): FoulCommitted {
        val foulCommitted = FoulCommitted()

        if (statsBombFoulCommitted.type != null) {
            foulCommitted.eventType = eventEntityService.getOrCreateEventType(statsBombFoulCommitted.type)
        }

        if (statsBombFoulCommitted.card != null) {
            foulCommitted.card = eventEntityService.getOrCreateCard(statsBombFoulCommitted.card)
        }

        foulCommitted.offensive = statsBombFoulCommitted.offensive
        foulCommitted.advantage = statsBombFoulCommitted.advantage
        foulCommitted.penalty = statsBombFoulCommitted.penalty

        return foulCommitted
    }

    private fun getFoulWon(statsBombFoulWon: StatsBombFoulWon): FoulWon {
        val foulWon = FoulWon()

        foulWon.defensive = statsBombFoulWon.defensive
        foulWon.advantage = statsBombFoulWon.advantage
        foulWon.penalty = statsBombFoulWon.penalty

        return foulWon
    }

    private fun getGoalKeeper(statsBombGoalkeeper: StatsBombGoalkeeper): GoalKeeper {
        val eventType = eventEntityService.getOrCreateEventType(statsBombGoalkeeper.type)

        val goalKeeper = GoalKeeper(eventType)

        if (statsBombGoalkeeper.endLocation != null) {
            goalKeeper.endLocation = Location2D(statsBombGoalkeeper.endLocation[0], statsBombGoalkeeper.endLocation[1])
        }

        if (statsBombGoalkeeper.position != null) {
            goalKeeper.position = eventEntityService.getOrCreatePosition(statsBombGoalkeeper.position)
        }

        if (statsBombGoalkeeper.outcome != null) {
            goalKeeper.outcome = eventEntityService.getOrCreateOutcome(statsBombGoalkeeper.outcome)
        }

        if (statsBombGoalkeeper.bodyPart != null) {
            goalKeeper.bodyPart = eventEntityService.getOrCreateBodyPart(statsBombGoalkeeper.bodyPart)
        }

        if (statsBombGoalkeeper.technique != null) {
            goalKeeper.technique = eventEntityService.getOrCreateTechnique(statsBombGoalkeeper.technique)
        }

        goalKeeper.shotSavedOffTarget = statsBombGoalkeeper.shotSavedOffTarget
        goalKeeper.punchedOut = statsBombGoalkeeper.punchedOut
        goalKeeper.shotSavedToPost = statsBombGoalkeeper.shotSavedToPost
        goalKeeper.lostInPlay = statsBombGoalkeeper.lostInPlay
        goalKeeper.successInPlay = statsBombGoalkeeper.successInPlay
        goalKeeper.savedToPost = statsBombGoalkeeper.savedToPost
        goalKeeper.lostOut = statsBombGoalkeeper.lostOut
        goalKeeper.successOut = statsBombGoalkeeper.successOut
        goalKeeper.penaltySavedToPost = statsBombGoalkeeper.penaltySavedToPost

        return goalKeeper
    }

    private fun getHalfEnd(statsBombHalfEnd: StatsBombHalfEnd): HalfEnd {
        val halfEnd = HalfEnd()

        halfEnd.earlyVideoEnd = statsBombHalfEnd.earlyVideoEnd

        return halfEnd
    }

    private fun getHalfStart(statsBombHalfStart: StatsBombHalfStart): HalfStart {
        return HalfStart(statsBombHalfStart.lateVideoStart)
    }

    private fun getInjuryStoppage(statsBombInjuryStoppage: StatsBombInjuryStoppage): InjuryStoppage {
        return InjuryStoppage(statsBombInjuryStoppage.inChain)
    }

    private fun getInterception(statsBombInterception: StatsBombInterception): Interception {
        val outcome = eventEntityService.getOrCreateOutcome(statsBombInterception.interception)

        return Interception(outcome)
    }

    private fun getMiscontrol(statsBombMiscontrol: StatsBombMiscontrol): Miscontrol {
        val miscontrol = Miscontrol()

        miscontrol.aerialWon = statsBombMiscontrol.aerialWon

        return miscontrol
    }

    private fun getPass(statsBombPass: StatsBombPass): Pass {
        val passHeight = eventEntityService.getOrCreatePassHeight(statsBombPass.passHeight)

        val pass = Pass(
                statsBombPass.length,
                statsBombPass.angle,
                passHeight,
                Location2D(statsBombPass.endLocation[0], statsBombPass.endLocation[1])
        )

        if (statsBombPass.recipient != null) {
            val recipient = baseEntityService.getPlayerByExternalId(statsBombPass.recipient.id.toString())
                    ?: throw StatsBombPlayerNotFound("${statsBombPass.recipient} not found. " +
                            "Player must exist before importing event data")

            pass.recipient = recipient
        }

        if (statsBombPass.bodyPart != null) {
            pass.bodyPart = eventEntityService.getOrCreateBodyPart(statsBombPass.bodyPart)
        }

        if (statsBombPass.type != null) {
            pass.eventType = eventEntityService.getOrCreateEventType(statsBombPass.type)
        }

        if (statsBombPass.outcome != null) {
            pass.outcome = eventEntityService.getOrCreateOutcome(statsBombPass.outcome)
        }

        if (statsBombPass.technique != null) {
            pass.technique = eventEntityService.getOrCreateTechnique(statsBombPass.technique)
        }

        if (statsBombPass.assistedShotId != null) {
            pass.assistedShot = eventEntityService.getEventById(statsBombPass.assistedShotId)
//                    ?: throw StatsBombEventNotFound("Event not found - ${statsBombPass.assistedShotId}")
        }

        pass.noTouch = statsBombPass.noTouch
        pass.isCross = statsBombPass.cross
        pass.switch = statsBombPass.switch
        pass.inswinging = statsBombPass.inswinging
        pass.outswinging = statsBombPass.outswinging
        pass.shotAssist = statsBombPass.shotAssist
        pass.aerialWon = statsBombPass.aerialWon
        pass.goalAssist = statsBombPass.goalAssist
        pass.cutBack = statsBombPass.cutBack
        pass.throughBall = statsBombPass.throughBall
        pass.miscommunication = statsBombPass.miscommunication
        pass.deflected = statsBombPass.deflected
        pass.straight = statsBombPass.straight
        pass.backheel = statsBombPass.backheel

        return pass
    }

    private fun getPlayer(statsBombPlayer: StatsBombPlayerSimple): Player {
        return baseEntityService.getPlayerByExternalId(statsBombPlayer.id.toString())
                ?: throw StatsBombPlayerNotFound("${statsBombPlayer.name} not found")
    }

    private fun getPlayerOff(statsBombPlayerOff: StatsBombPlayerOff): PlayerOff {
        val playerOff = PlayerOff()

        playerOff.permanent = statsBombPlayerOff.permanent

        return playerOff
    }

    private fun getPosition(statsBombPosition: StatsBombPosition): Position {
        return eventEntityService.getOrCreatePosition(statsBombPosition)
    }

    private fun getShot(statsBombShot: StatsBombShot): Shot {
        val shot = Shot(
                statsBombShot.statsbombXg,
                getShotEndLocation(statsBombShot.endLocation),
                eventEntityService.getOrCreateTechnique(statsBombShot.technique),
                eventEntityService.getOrCreateOutcome(statsBombShot.outcome),
                eventEntityService.getOrCreateEventType(statsBombShot.type),
                eventEntityService.getOrCreateBodyPart(statsBombShot.bodyPart)
        )

        shot.freezeFrame = getFreezeFrames(statsBombShot.freezeFrame)

        if (statsBombShot.keyPassId != null) {
            shot.keyPass = eventEntityService.getEventById(statsBombShot.keyPassId)
                    ?: throw StatsBombEventNotFound("Event not found ${statsBombShot.keyPassId}")
        }

        shot.firstTime = statsBombShot.firstTime
        shot.openGoal = statsBombShot.openGoal
        shot.oneOnOne = statsBombShot.oneOnOne
        shot.aerialWon = statsBombShot.aerialWon
        shot.deflected = statsBombShot.deflected
        shot.followsDribble = statsBombShot.followsDribble
        shot.savedOffTarget = statsBombShot.savedOffTarget
        shot.savedToPost = statsBombShot.savedToPost
        shot.redirect = statsBombShot.redirect
        shot.kickOff = statsBombShot.kickOff

        return shot
    }

    private fun getShotEndLocation(endLocation: Array<Double>): Location3D {
        return Location3D(
                endLocation[0],
                endLocation[1],
                if (endLocation.size == 3) endLocation[2] else 0.0
        )
    }

    private fun getFreezeFrames(statsBombFreezeFrames: Iterable<StatsBombFreezeFrame>?): MutableSet<FreezeFrame> {
        if (statsBombFreezeFrames == null) return mutableSetOf()

        return statsBombFreezeFrames.map {
            val player = baseEntityService.getPlayerByExternalId(it.player.id.toString())
                    ?: throw StatsBombPlayerNotFound("${it.player.name} not found")

            FreezeFrame(
                    Location2D(it.location[0], it.location[1]),
                    player,
                    eventEntityService.getOrCreatePosition(it.position),
                    it.teammate
            )
        }.toMutableSet()
    }

    private fun getSubstitution(statsBombSubstitution: StatsBombSubstitution): Substitution {
        val replacement = baseEntityService.getPlayerByExternalId(statsBombSubstitution.replacement.id.toString())
                ?: throw StatsBombPlayerNotFound("${statsBombSubstitution.replacement.name} not found. " +
                        "Player must exist before importing events")

        val substitution = Substitution(replacement)

        if (statsBombSubstitution.outcome != null) {
            substitution.outcome = eventEntityService.getOrCreateOutcome(statsBombSubstitution.outcome)
        }

        return substitution
    }

    private fun getTactics(statsBombTactics: StatsBombTactics): Tactics {
        val tactics = Tactics(statsBombTactics.formation)

        tactics.lineup = statsBombTactics.lineup.map {
            val player = baseEntityService.getPlayerByExternalId(it.playerSimple.id.toString())
                    ?: throw StatsBombPlayerNotFound("${it.playerSimple.name} not found. " +
                            "Player must exist before importing events")

            TacticalLineupPlayer(
                    it.jerseyNumber.toInt(),
                    player,
                    eventEntityService.getOrCreatePosition(it.position)
            )
        }.toMutableSet()

        return tactics
    }

    private fun getLocation(statsBombLocation: Array<Double>): Location2D {
        return Location2D(statsBombLocation[0], statsBombLocation[1])
    }

    private fun setOptionalFlags(event: Event, statsBombEvent: StatsBombEvent) {
        event.underPressure = statsBombEvent.underPressure
        event.counterPress = statsBombEvent.counterPress
        event.offCamera = statsBombEvent.offCamera
    }

    private fun setMatchRelatedEvents(
            eventEntities: HashMap<String, Event>,
            statsBombEvents: Iterable<StatsBombEvent>
    ) {
        statsBombEvents
                .filter { it.relatedEvents != null }
                .forEach {
                    val eventEntity = eventEntities[it.id] ?: throw StatsBombEventNotFound("Event not found $it")

                    eventEntity.relatedEvents = getRelatedEvents(it.relatedEvents, eventEntities)

                    eventEntityService.save(eventEntity)
                }
    }

    private fun getRelatedEvents(
            statsBombEventIds: Iterable<String>?,
            eventEntities: HashMap<String, Event>
    ): MutableSet<Event> {
        if (statsBombEventIds == null) return mutableSetOf()

        return statsBombEventIds
                .map {eventEntities[it] ?: throw StatsBombEventNotFound("Event not found $it") }
                .toMutableSet()
    }
}