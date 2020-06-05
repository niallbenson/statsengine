package com.footiestats.statsengine.services.feed.statsbomb.feeds.events

import com.footiestats.statsengine.entities.engine.events.Event

data class StatsBombTransientEventData(
        val event: Event,
        val relatedEventIds: Iterable<String>?,
        val passAssistedShotEventId: String?,
        val shotKeyPassId: String?)