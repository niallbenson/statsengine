package com.footiestats.statsengine.dtos.engine.mappers

import com.footiestats.statsengine.dtos.engine.TeamDTO
import com.footiestats.statsengine.entities.engine.Team

class TeamMapper {
    companion object {
        fun toDto(team: Team): TeamDTO {
            return TeamDTO(
                    team.id ?: -1,
                    team.name
            )
        }
    }
}