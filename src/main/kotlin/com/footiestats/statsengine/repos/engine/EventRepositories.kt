package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.Match
import com.footiestats.statsengine.entities.engine.Player
import com.footiestats.statsengine.entities.engine.events.Event
import com.footiestats.statsengine.entities.engine.events.EventType
import com.footiestats.statsengine.entities.engine.events.metadata.*
import com.footiestats.statsengine.entities.engine.events.refdata.*
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface EventRepository : PagingAndSortingRepository<Event, Long> {
    fun findBySourceExternalId(id: String): Event?
    fun findAllByMatch(match: Match): Set<Event>
    fun countAllByMatch(match: Match): Long
    fun findAllByMatch_Id(matchId: Long): Set<Event>
    fun findAllByPlayer_IdAndMatch_Id(playerId: Long, matchId: Long): Set<Event>
    fun findAllByMatch_IdAndType_Id(matchId: Long, eventTypeId: Long): Set<Event>
    fun findAllByMatch_IdAndType_IdAndEventTeam_Id(matchId: Long, eventTypeId: Long, teamId: Long): Set<Event>
    fun findAllByMatch_IdAndType_IdAndShot_Outcome_IdAndEventTeam_IdOrderByEventIndex(
            matchId: Long, eventTypeId: Long, outcomeId: Long, teamId: Long): Set<Event>

    fun findByMatch_IdAndEventIndex(matchId: Long, eventIndex: Int): Event

    @Query("select l from Event e join e.tactics t join t.lineup l " +
            "where e.match.id  = ?1 and l.player.id = ?2 and e.eventIndex < ?3 and e.type.id in (?4, ?5) " +
            "order by e.eventIndex desc")
    fun getTacticalLineupPlayerAtEventIndex(
            matchId: Long, playerId: Long, eventIndex: Int,
            statingXiTypeId: Long, tacticalShiftId: Long,
            pageable: Pageable
    ): ArrayList<TacticalLineupPlayer>
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

interface SubstitutionRepository : PagingAndSortingRepository<Substitution, Long> {
    fun findByEvent_MatchAndReplacement(match: Match, replacement: Player): Substitution
}

interface TacticalLineupPlayerRepository : PagingAndSortingRepository<TacticalLineupPlayer, Long> {
    @Query("select l " +
            "from Tactics t " +
            "join t.lineup l " +
            "where l.player.id = ?1 and t.event.match.id = ?2 " +
            "order by t.event.eventIndex")
    fun getPlayerMatchTacticalLineups(playerId: Long, matchId: Long, pageable: Pageable): ArrayList<TacticalLineupPlayer>
}

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
