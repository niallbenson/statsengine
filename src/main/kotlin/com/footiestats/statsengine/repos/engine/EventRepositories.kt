package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.enums.OutcomeEnum
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface EventRepository : PagingAndSortingRepository<Event, Long> {
    fun findBySourceExternalId(id: String): Event?
    fun findAllByMatch(match: Match): ArrayList<Event>
    fun countAllByMatch(match: Match): Long
    fun findAllByPlayer_IdAndMatch_Id(playerId: Long, matchId: Long): ArrayList<Event>
    fun findAllByMatch_IdAndType_Id(matchId: Long, eventTypeId: Long): ArrayList<Event>
    fun findAllByMatch_IdAndType_IdAndEventTeam_Id(matchId: Long, eventTypeId: Long, teamId: Long): ArrayList<Event>
    fun findAllByMatch_IdAndType_IdAndShot_Outcome_IdAndEventTeam_IdOrderByEventIndex(
            matchId: Long, eventTypeId: Long, outcomeId: Long, teamId: Long): ArrayList<Event>

//    @Query("from Event e " +
//            "join fetch e.shot s " +
//            "join fetch s.outcome o " +
//            "where e.match.id = ?1 " +
//            "e.type.id = com.footiestats.statsengine.entities.engine.enums.EventTypeEnum.SHOT.id " +
//            "and o.id = com.footiestats.statsengine.entities.engine.enums.OutcomeEnum.GOAL.id " +
//            "and e.eventTeam.id = ?2 " +
//            "union " +
//            "from Event e" +
//            "where e.match.id = ?1 " +
//            "e.type.id = com.footiestats.statsengine.entities.engine.enums.EventTypeEnum.OWN_GOAL_AGAINST.id " +
//            "and e.eventTeam.id != ?2")
//    fun getMatchTeamGoals(matchId: Long, teamId: Long): ArrayList<Event>
}

interface EventTypeRepository : PagingAndSortingRepository<EventType, Long> {
    fun findBySourceExternalId(id: String): EventType?
}

interface BadBehaviourRepository : PagingAndSortingRepository<BadBehaviour, Long>

interface BallReceiptRepository : PagingAndSortingRepository<BallReceipt, Long>

interface BallRecoveryRepository : PagingAndSortingRepository<BallRecovery, Long>

interface BlockRepository : PagingAndSortingRepository<Block, Long>

interface BodyPartRepository : PagingAndSortingRepository<BodyPart, Long> {
    fun findBySourceExternalId(id: String): BodyPart?
}

interface CardRepository : PagingAndSortingRepository<Card, Long> {
    fun findBySourceExternalId(id: String): Card?
}

interface CarryRepository : PagingAndSortingRepository<Carry, Long>

interface ClearanceRepository : PagingAndSortingRepository<Clearance, Long>

interface DribbleRepository : PagingAndSortingRepository<Dribble, Long>

interface DuelRepository : PagingAndSortingRepository<Duel, Long>

interface FiftyFiftyRepository : PagingAndSortingRepository<FiftyFifty, Long>

interface FoulCommittedRepository : PagingAndSortingRepository<FoulCommitted, Long>

interface FoulWonRepository : PagingAndSortingRepository<FoulWon, Long>

interface FreezeFrameRepository : PagingAndSortingRepository<FreezeFrame, Long>

interface GoalKeeperRepository : PagingAndSortingRepository<GoalKeeper, Long>

interface HalfEndRepository : PagingAndSortingRepository<HalfEnd, Long>

interface HalfStartRepository : PagingAndSortingRepository<HalfStart, Long>

interface InjuryStoppageRepository : PagingAndSortingRepository<InjuryStoppage, Long>

interface InterceptionRepository : PagingAndSortingRepository<Interception, Long>

interface Location2DRepository : PagingAndSortingRepository<Location2D, Long>

interface MiscontrolRepository : PagingAndSortingRepository<Miscontrol, Long>

interface OutcomeRepository : PagingAndSortingRepository<Outcome, Long> {
    fun findBySourceExternalId(id: String): Outcome?
}

interface PassRepository : PagingAndSortingRepository<Pass, Long>

interface PassHeightRepository : PagingAndSortingRepository<PassHeight, Long> {
    fun findBySourceExternalId(id: String): PassHeight?
}

interface PlayerOffRepository : PagingAndSortingRepository<PlayerOff, Long>

interface PlayPatternRepository : PagingAndSortingRepository<PlayPattern, Long> {
    fun findBySourceExternalId(id: String): PlayPattern?
}

interface PositionRepository : PagingAndSortingRepository<Position, Long> {
    fun findBySourceExternalId(id: String): Position?
}

interface ShotRepository : PagingAndSortingRepository<Shot, Long>

interface SubstitutionRepository : PagingAndSortingRepository<Substitution, Long>

interface TacticalLineupPlayerRepository : PagingAndSortingRepository<TacticalLineupPlayer, Long>

interface TacticsRepository : PagingAndSortingRepository<Tactics, Long> {
    fun findAllByEvent_Match_IdAndEvent_EventTeam_Id(matchId: Long, teamId: Long): ArrayList<Tactics>
}

interface TechniqueRepository : PagingAndSortingRepository<Technique, Long> {
    fun findBySourceExternalId(id: String): Technique?
}

interface DuelTypeRepository : PagingAndSortingRepository<DuelType, Long> {
    fun findBySourceExternalId(id: String): DuelType?
}

interface FoulCommittedTypeRepository : PagingAndSortingRepository<FoulCommittedType, Long> {
    fun findBySourceExternalId(id: String): FoulCommittedType?
}

interface GoalkeeperTypeRepository : PagingAndSortingRepository<GoalkeeperType, Long> {
    fun findBySourceExternalId(id: String): GoalkeeperType?
}

interface PassTypeRepository : PagingAndSortingRepository<PassType, Long> {
    fun findBySourceExternalId(id: String): PassType?
}

interface ShotTypeRepository : PagingAndSortingRepository<ShotType, Long> {
    fun findBySourceExternalId(id: String): ShotType?
}