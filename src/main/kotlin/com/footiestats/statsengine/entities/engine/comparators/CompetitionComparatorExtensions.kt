package com.footiestats.statsengine.entities.engine.comparators

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition

class CompetitionComparators {
    companion object {
        fun compareToStatsBombCompetition(
                competition: Competition, statsBombCompetition: StatsBombCompetition): Boolean =
                competition.country.name.equals(statsBombCompetition.countryName)
                        && competition.season.name.equals(statsBombCompetition.seasonName)
                        && competition.name.equals(statsBombCompetition.competitionName)
    }
}