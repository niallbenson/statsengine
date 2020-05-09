package com.footiestats.statsengine.repos.engine

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.engine.Country
import com.footiestats.statsengine.entities.engine.Season
import org.springframework.data.repository.CrudRepository

interface CompetitionRepository : CrudRepository<Competition, Long>
interface CountryRepository : CrudRepository<Country, Long>
interface SeasonRepository : CrudRepository<Season, Long>