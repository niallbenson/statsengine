package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.LineupPlayerDTO
import com.footiestats.statsengine.entities.engine.LineupPlayer
import org.springframework.stereotype.Service

@Service
class LineupPlayerMapper {

    fun toDto(lineupPlayer: LineupPlayer): LineupPlayerDTO {
        return LineupPlayerDTO(
                lineupPlayer.player.id ?: -1,
                lineupPlayer.player.name,
                lineupPlayer.player.nickName,
                lineupPlayer.player.id ?: -1,
                lineupPlayer.jerseyNumber
        )
    }
}