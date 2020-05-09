package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.repos.engine.*
import org.springframework.stereotype.Service

@Service
class StatsBombMatchFeedService(
        private val matchRepository: MatchRepository,
        private val competitionRepository: CompetitionRepository,
        private val seasonRepository: SeasonRepository,
        private val teamRepository: TeamRepository,
        private val countryRepository: CountryRepository,
        private val managerRepository: ManagerRepository,
        private val metadataRepository: MetadataRepository,
        private val competitionStageRepository: CompetitionStageRepository,
        private val stadiumRepository: StadiumRepository,
        private val refereeRepository: RefereeRepository) {

}