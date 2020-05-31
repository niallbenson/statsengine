package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.dtos.statsbomb.StatsBombCard
import com.footiestats.statsengine.dtos.statsbomb.StatsBombEventType
import com.footiestats.statsengine.dtos.statsbomb.StatsBombOutcome
import com.footiestats.statsengine.dtos.statsbomb.StatsBombPlayPattern
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.refdata.Outcome
import com.footiestats.statsengine.entities.engine.events.refdata.Card
import com.footiestats.statsengine.entities.engine.events.refdata.PlayPattern
import com.footiestats.statsengine.repos.engine.*
import com.footiestats.statsengine.services.feed.statsbomb.exceptions.StatsBombEntityNotFound
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
        private val heightRepository: HeightRepository,
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
}