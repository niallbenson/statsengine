package com.footiestats.statsengine.entities.engine.comparators

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition

open class Competition

class CompetitionComparators {
    fun Competition.compareToStatsBombCompetition(statsBombCompetition: StatsBombCompetition): Boolean =
            this.country.name.equals(statsBombCompetition.countryName)
                    && this.season.name.equals(statsBombCompetition.seasonName)
                    && this.name.equals(statsBombCompetition.competitionName)
}