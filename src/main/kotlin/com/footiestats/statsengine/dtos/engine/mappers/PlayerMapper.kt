package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.PlayerDTO
import com.footiestats.statsengine.entities.engine.Player
import org.springframework.stereotype.Service

@Service
class PlayerMapper {

    fun toDto(player: Player): PlayerDTO {

        return PlayerDTO(
                player.id ?: -1,
                player.name,
                player.nickName,
                player.country?.name
        )
    }
}