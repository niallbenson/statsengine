package com.footiestats.statsengine.dtos.engine.eventtimeline

import com.footiestats.statsengine.dtos.engine.PlayerDTO

data class PlayerMatchTimelineDTO(
        val player: PlayerDTO,
        val items: Array<TimelineItemDTO>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerMatchTimelineDTO

        if (player != other.player) return false
        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + items.contentHashCode()
        return result
    }
}