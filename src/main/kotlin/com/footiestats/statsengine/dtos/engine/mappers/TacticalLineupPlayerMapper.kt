package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.TacticalLineupPlayerDTO
import com.footiestats.statsengine.entities.engine.events.metadata.TacticalLineupPlayer
import org.springframework.stereotype.Service

@Service
class TacticalLineupPlayerMapper {

    fun toDto(tacticalLineupPlayer: TacticalLineupPlayer): TacticalLineupPlayerDTO {

        return TacticalLineupPlayerDTO(
                tacticalLineupPlayer.id ?: -1,
                tacticalLineupPlayer.player.name,
                tacticalLineupPlayer.player.nickName,
                tacticalLineupPlayer.player.id ?: -1,
                17,
                tacticalLineupPlayer.position.id ?: -1,
                tacticalLineupPlayer.position.name
        )
    }
}