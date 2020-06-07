package com.footiestats.statsengine.dtos.engine

data class MatchLineupDTO(
        val id: Long,
        val team: TeamDTO,
        val players: Array<LineupPlayerDTO>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatchLineupDTO

        if (id != other.id) return false
        if (team != other.team) return false
        if (!players.contentEquals(other.players)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + team.hashCode()
        result = 31 * result + players.contentHashCode()
        return result
    }
}