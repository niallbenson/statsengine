package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.feed.statsbomb.feeds.StatsBombCompetitionFeedService
import com.footiestats.statsengine.services.feed.statsbomb.feeds.StatsBombEventFeedService
import com.footiestats.statsengine.services.feed.statsbomb.feeds.StatsBombLineupsFeedService
import com.footiestats.statsengine.services.feed.statsbomb.feeds.StatsBombMatchFeedService
//import com.footiestats.statsengine.services.feed.statsbomb.feeds.StatsBombMatchFeedService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/feed")
class FeedController(
        private val statsBombCompetitionFeedService: StatsBombCompetitionFeedService,
        private val statsBombMatchFeedService: StatsBombMatchFeedService,
        private val statsBombLineupsFeedService: StatsBombLineupsFeedService,
        private val statsBombEventFeedService: StatsBombEventFeedService
) {
    @GetMapping("/statsbomb/competitions")
    fun updateStatsbombCompetitions() = statsBombCompetitionFeedService.run()

    @GetMapping("/statsbomb/matches")
    fun updateStatsbombMatches() = statsBombMatchFeedService.run()

    @GetMapping("/statsbomb/lineups")
    fun updateStatsbombLinesups() = statsBombLineupsFeedService.run()

    @GetMapping("/statsbomb/events")
    fun updateStatsbombEvents() = statsBombEventFeedService.run()

}