package com.footiestats.statsengine.services.feed.statsbomb.feeds

import com.footiestats.statsengine.dtos.statsbomb.StatsBombEvent
import com.footiestats.statsengine.dtos.statsbomb.StatsBombEventType
import com.footiestats.statsengine.entities.engine.CompetitionSeason
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombEntityService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombRestService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class StatsBombEventFeedService(
        private val entityService: StatsBombEntityService,
        private val restService: StatsBombRestService) {

    fun run(): Iterable<StatsBombEvent> {
        log.info { "Starting StatsBomb Events Feed" }

        val events = ArrayList<StatsBombEvent>()

        val competitionSeasons = entityService.getCompetitionSeasons()

//        for (cs in competitionSeasons) {
            events.addAll(processCompetitionSeason(competitionSeasons.iterator().next()))
//        }

        class EventType(val id: Int, val name: String)

        val types = HashSet<EventType>()

        for (s in events.map { it.type }) {
            if (types.count { it.id == s.id } == 0) {
                types.add(EventType(s.id, s.name))
            }
        }

        val typeNameEventsMap = HashMap<String, Array<StatsBombEvent>>()

        for (t in types) {
            log.info { "${t.id}: ${t.name}" }

            typeNameEventsMap.set(
                    t.name,
                    events.filter { it.type.id == t.id }.toTypedArray()
            )
        }

        return events
    }

    private fun processCompetitionSeason(competitionSeason: CompetitionSeason): Iterable<StatsBombEvent> {
        log.info {
            "Importing events for competition=${competitionSeason.competition.name} " +
                    "season=${competitionSeason.season.name}"
        }

        val events = ArrayList<StatsBombEvent>()

        val matches = entityService.getMatchesForCompetitionSeason(competitionSeason)

        for (m in matches.subList(0, 5)) {
            log.info { "Processing match ID ${m.id} ${m.homeTeam.name} vs ${m.awayTeam.name} ${m.matchDate}" }

            events.addAll(restService.getStatsBombEvents(m.sourceExternalId))

            log.info { "${events.size} events found" }
        }

        return events
    }


}