package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.LineupPlayerDTO
import com.footiestats.statsengine.entities.engine.LineupPlayer

class LineupPlayerMapper {
    companion object {
        fun toDto(lineupPlayer: LineupPlayer): LineupPlayerDTO {
            return LineupPlayerDTO(
                    lineupPlayer.id ?: -1,
                    lineupPlayer.player.name,
                    lineupPlayer.player.nickName,
                    lineupPlayer.player.id ?: -1,
                    lineupPlayer.jerseyNumber
            )
        }
    }
}