package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.*
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.PassHeight
import com.footiestats.statsengine.entities.engine.events.metadata.Technique
import com.footiestats.statsengine.entities.engine.events.refdata.Position
import com.footiestats.statsengine.entities.engine.events.refdata.BodyPart
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import com.footiestats.statsengine.entities.engine.events.refdata.Card
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StatsBombEventEntityService(
        private val sourceRepository: SourceRepository,
        private val eventRepository: EventRepository,
        private val eventTypeRepository: EventTypeRepository,
        private val badBehaviourRepository: BadBehaviourRepository,
        private val ballReceiptRepository: BallReceiptRepository,
        private val ballRecoveryRepository: BallRecoveryRepository,
        private val blockRepository: BlockRepository,
        private val bodyPartRepository: BodyPartRepository,
        private val cardRepository: CardRepository,
        private val carryRepository: CarryRepository,
        private val clearanceRepository: ClearanceRepository,
        private val dribbleRepository: DribbleRepository,
        private val duelRepository: DuelRepository,
        private val fiftyFiftyRepository: FiftyFiftyRepository,
        private val foulCommittedRepository: FoulCommittedRepository,
        private val foulWonRepository: FoulWonRepository,
        private val freezeFrameRepository: FreezeFrameRepository,
        private val goalKeeperRepository: GoalKeeperRepository,
        private val halfEndRepository: HalfEndRepository,
        private val halfStartRepository: HalfStartRepository,
        private val passHeightRepository: PassHeightRepository,
        private val injuryStoppageRepository: InjuryStoppageRepository,
        private val interceptionRepository: InterceptionRepository,
        private val location2DRepository: Location2DRepository,
        private val miscontrolRepository: MiscontrolRepository,
        private val outcomeRepository: OutcomeRepository,
        private val passRepository: PassRepository,
        private val playerOffRepository: PlayerOffRepository,
        private val playPatternRepository: PlayPatternRepository,
        private val positionRepository: PositionRepository,
        private val shotRepository: ShotRepository,
        private val substitutionRepository: SubstitutionRepository,
        private val tacticalLineupPlayerRepository: TacticalLineupPlayerRepository,
        private val tacticsRepository: TacticsRepository,
        private val techniqueRepository: TechniqueRepository
) {
    // Source
    fun getStatsBombSource() = sourceRepository.findByName("StatsBomb")
            ?: throw StatsBombEntityNotFound("Could not load StatsBomb entity")

    // Event
    fun save(event: Event) = eventRepository.save(event)
    fun getEventById(id: String) = eventRepository.findBySourceExternalId(id)

    // Event Type
    fun save(eventType: EventType) = eventTypeRepository.save(eventType)

    fun getOrCreateEventType(statsBombEventType: StatsBombEventType): EventType {
        var eventType = eventTypeRepository.findBySourceExternalId(statsBombEventType.id.toString())

        if (eventType == null) {
            eventType = eventTypeRepository.save(
                    EventType(statsBombEventType.name, getStatsBombSource(), statsBombEventType.id.toString())
            )
        }
        return eventType
    }

    // Play Pattern
    fun save(playPattern: PlayPattern) = playPatternRepository.save(playPattern)

    fun getOrCreatePlayPattern(statsBombPlayPattern: StatsBombPlayPattern): PlayPattern {
        var playPattern = playPatternRepository.findBySourceExternalId(statsBombPlayPattern.id.toString())

        if (playPattern == null) {
            playPattern = playPatternRepository.save(
                    PlayPattern(statsBombPlayPattern.name, getStatsBombSource(), statsBombPlayPattern.id.toString())
            )
        }
        return playPattern
    }

    // Card
    fun getOrCreateCard(statsBombCard: StatsBombCard): Card {
        var card = cardRepository.findBySourceExternalId(statsBombCard.id.toString())

        if (card == null) {
            card = cardRepository.save(
                    Card(statsBombCard.name, getStatsBombSource(), statsBombCard.id.toString())
            )
        }
        return card
    }

    // Outcome
    fun getOrCreateOutcome(statsBombOutcome: StatsBombOutcome): Outcome {
        var outcome = outcomeRepository.findBySourceExternalId(statsBombOutcome.id.toString())

        if (outcome == null) {
            outcome = outcomeRepository.save(
                    Outcome(statsBombOutcome.name, getStatsBombSource(), statsBombOutcome.id.toString())
            )
        }
        return outcome
    }

    // Body Part
    fun getOrCreateBodyPart(statsBombBodyPart: StatsBombBodyPart): BodyPart {
        var bodyPart = bodyPartRepository.findBySourceExternalId(statsBombBodyPart.id.toString())

        if (bodyPart == null) {
            bodyPart = bodyPartRepository.save(
                    BodyPart(statsBombBodyPart.name, getStatsBombSource(), statsBombBodyPart.id.toString())
            )
        }
        return bodyPart
    }

    // Position
    fun getOrCreatePosition(statsBombPosition: StatsBombPosition): Position {
        var position = positionRepository.findBySourceExternalId(statsBombPosition.id.toString())

        if (position == null) {
            position = positionRepository.save(
                    Position(statsBombPosition.name, getStatsBombSource(), statsBombPosition.id.toString())
            )
        }
        return position
    }

    // Technique
    fun getOrCreateTechnique(statsBombTechnique: StatsBombTechnique): Technique {
        var technique = techniqueRepository.findBySourceExternalId(statsBombTechnique.id.toString())

        if (technique == null) {
            technique = techniqueRepository.save(
                    Technique(statsBombTechnique.name, getStatsBombSource(), statsBombTechnique.id.toString())
            )
        }
        return technique
    }

    // Pass Height
    fun getOrCreatePassHeight(statsBombPassHeight: StatsBombPassHeight): PassHeight {
        var passHeight = passHeightRepository.findBySourceExternalId(statsBombPassHeight.id.toString())

        if (passHeight == null) {
            passHeight = passHeightRepository.save(
                    PassHeight(statsBombPassHeight.name, getStatsBombSource(), statsBombPassHeight.id.toString())
            )
        }
        return passHeight
    }
}