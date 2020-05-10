package com.footiestats.statsengine.controllers

import com.footiestats.statsengine.services.feed.statsbomb.StatsBombCompetitionFeedService
import com.footiestats.statsengine.services.feed.statsbomb.StatsBombMatchFeedService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/feed")
class FeedController(
        private val statsBombCompetitionFeedService: StatsBombCompetitionFeedService,
        private val statsBombMatchFeedService: StatsBombMatchFeedService
) {
    @GetMapping("/statsbomb/competitions")
    fun updateStatsbombCompetitions() = statsBombCompetitionFeedService.updateFromStatsBombCompetitions()

    @GetMapping("/statsbomb/matches")
    fun updateStatsbombMatches() = statsBombMatchFeedService.updateAllCompetitionSeasons()
}