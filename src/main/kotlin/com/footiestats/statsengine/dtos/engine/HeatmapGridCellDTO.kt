package com.footiestats.statsengine.dtos.engine

data class HeatmapGridCellDTO(
        val x: Int,
        val y: Int,
        val value: Int,
        val eventId: Array<Long>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeatmapGridCellDTO

        if (x != other.x) return false
        if (y != other.y) return false
        if (value != other.value) return false
        if (!eventId.contentEquals(other.eventId)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + value
        result = 31 * result + eventId.contentHashCode()
        return result
    }
}
