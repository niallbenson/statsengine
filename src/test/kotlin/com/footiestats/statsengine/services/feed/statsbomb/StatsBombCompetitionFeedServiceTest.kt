package com.footiestats.statsengine.services.feed.statsbomb

import com.footiestats.statsengine.entities.engine.Competition
import com.footiestats.statsengine.entities.engine.Country
import com.footiestats.statsengine.entities.engine.Season
import com.footiestats.statsengine.entities.engine.Source
import com.footiestats.statsengine.entities.engine.enums.Gender
import com.footiestats.statsengine.entities.statsbomb.StatsBombCompetition
import com.footiestats.statsengine.repos.engine.CompetitionRepository
import com.footiestats.statsengine.repos.engine.CountryRepository
import com.footiestats.statsengine.repos.engine.SeasonRepository
import com.footiestats.statsengine.repos.engine.SourceRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI

@ExtendWith(SpringExtension::class)
internal class StatsBombCompetitionFeedServiceTest {

    @RelaxedMockK
    private lateinit var competitionRepository: CompetitionRepository
    @RelaxedMockK
    private lateinit var countryRepository: CountryRepository
    @RelaxedMockK
    private lateinit var seasonRepository: SeasonRepository
    @RelaxedMockK
    private lateinit var sourceRepository: SourceRepository
    @RelaxedMockK
    private lateinit var restTemplate: RestTemplate

    @InjectMockKs
    private lateinit var service: StatsBombCompetitionFeedService

    @Test
    fun updateFromStatsBombCompetitions() {

        MockKAnnotations.init(this)

        val uri = URI("https://raw.githubusercontent.com/statsbomb/open-data/master/data/competitions.json")
        val json = getStatsBombCompetitionsJson()

        every { restTemplate.getForObject<String>(uri) } returns json

        val source = Source("StatsBomb")
        every { sourceRepository.findByName("StatsBomb") } returns source

        every { seasonRepository.findBySource(source) } returns ArrayList()
        val season = Season("name", source, "1")
        every { seasonRepository.save(any<Season>()) } returns season

        every { countryRepository.findAll() } returns ArrayList()
        val country = Country("name")
        every { countryRepository.save(any<Country>()) } returns country

        every { competitionRepository.findAllBySource(source) } returns ArrayList()
        every { competitionRepository.save(any<Competition>()) } returns
                Competition(season, country, "name", Gender.MALE, source, "1")

        val result = service.updateFromStatsBombCompetitions()

        println(result.iterator().hasNext())
    }

    private fun getStatsBombCompetitionsJson() =
            "[ {\n" +
                    "  \"competition_id\" : 37,\n" +
                    "  \"season_id\" : 42,\n" +
                    "  \"country_name\" : \"England\",\n" +
                    "  \"competition_name\" : \"FA Women's Super League\",\n" +
                    "  \"competition_gender\" : \"female\",\n" +
                    "  \"season_name\" : \"2019/2020\",\n" +
                    "  \"match_updated\" : \"2020-03-11T14:09:41.932138\",\n" +
                    "  \"match_available\" : \"2020-03-11T14:09:41.932138\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 37,\n" +
                    "  \"season_id\" : 4,\n" +
                    "  \"country_name\" : \"England\",\n" +
                    "  \"competition_name\" : \"FA Women's Super League\",\n" +
                    "  \"competition_gender\" : \"female\",\n" +
                    "  \"season_name\" : \"2018/2019\",\n" +
                    "  \"match_updated\" : \"2020-04-23T19:04:49.880742\",\n" +
                    "  \"match_available\" : \"2020-04-23T19:04:49.880742\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 43,\n" +
                    "  \"season_id\" : 3,\n" +
                    "  \"country_name\" : \"International\",\n" +
                    "  \"competition_name\" : \"FIFA World Cup\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2018\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 4,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2018/2019\",\n" +
                    "  \"match_updated\" : \"2020-04-21T21:40:19.168478\",\n" +
                    "  \"match_available\" : \"2020-04-21T21:40:19.168478\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 1,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2017/2018\",\n" +
                    "  \"match_updated\" : \"2020-05-03T17:05:26.772937\",\n" +
                    "  \"match_available\" : \"2020-05-03T17:05:26.772937\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 2,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2016/2017\",\n" +
                    "  \"match_updated\" : \"2020-04-01T14:15:08.846728\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 27,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2015/2016\",\n" +
                    "  \"match_updated\" : \"2020-04-13T23:02:59.803428\",\n" +
                    "  \"match_available\" : \"2020-04-13T23:02:59.803428\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 26,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2014/2015\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 25,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2013/2014\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 24,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2012/2013\",\n" +
                    "  \"match_updated\" : \"2020-04-13T23:02:59.803428\",\n" +
                    "  \"match_available\" : \"2020-04-13T23:02:59.803428\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 23,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2011/2012\",\n" +
                    "  \"match_updated\" : \"2020-04-13T23:02:59.803428\",\n" +
                    "  \"match_available\" : \"2020-04-13T23:02:59.803428\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 22,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2010/2011\",\n" +
                    "  \"match_updated\" : \"2020-04-09T13:13:49.345111\",\n" +
                    "  \"match_available\" : \"2020-04-09T13:13:49.345111\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 21,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2009/2010\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 41,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2008/2009\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 40,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2007/2008\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 39,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2006/2007\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 38,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2005/2006\",\n" +
                    "  \"match_updated\" : \"2020-02-27T12:19:39.458017\",\n" +
                    "  \"match_available\" : \"2020-02-27T12:19:39.458017\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 11,\n" +
                    "  \"season_id\" : 37,\n" +
                    "  \"country_name\" : \"Spain\",\n" +
                    "  \"competition_name\" : \"La Liga\",\n" +
                    "  \"competition_gender\" : \"male\",\n" +
                    "  \"season_name\" : \"2004/2005\",\n" +
                    "  \"match_updated\" : \"2019-12-16T23:09:16.168756\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 49,\n" +
                    "  \"season_id\" : 3,\n" +
                    "  \"country_name\" : \"United States of America\",\n" +
                    "  \"competition_name\" : \"NWSL\",\n" +
                    "  \"competition_gender\" : \"female\",\n" +
                    "  \"season_name\" : \"2018\",\n" +
                    "  \"match_updated\" : \"2020-02-27T15:22:21.167136\",\n" +
                    "  \"match_available\" : \"2019-12-16T23:09:16.168756\"\n" +
                    "}, {\n" +
                    "  \"competition_id\" : 72,\n" +
                    "  \"season_id\" : 30,\n" +
                    "  \"country_name\" : \"International\",\n" +
                    "  \"competition_name\" : \"Women's World Cup\",\n" +
                    "  \"competition_gender\" : \"female\",\n" +
                    "  \"season_name\" : \"2019\",\n" +
                    "  \"match_updated\" : \"2020-02-27T12:19:39.458017\",\n" +
                    "  \"match_available\" : \"2020-02-27T12:19:39.458017\"\n" +
                    "} ]"

    @Test
    fun `compareTo expect true`() {
        val statsBombCompetition = StatsBombCompetition(
                1, 1, "England", "EPL", "male",
                "1999-2000", "date", "date")

        val source = Source("source")
        val competition = Competition(
                Season("1999-2000", source, "1"),
                Country("England"),
                "EPL",
                Gender.MALE,
                source,
                "1")

        assertTrue(competition.compareTo(statsBombCompetition, source))
    }

    @Test
    fun `compareTo expect false`() {
        val statsBombCompetition = StatsBombCompetition(
                1, 1, "England", "EPL", "male",
                "1998-1999", "date", "date")

        val source = Source("source")
        val competition = Competition(
                Season("1999-2000", source, "1"),
                Country("England"),
                "EPL",
                Gender.MALE,
                source,
                "1")

        assertFalse(competition.compareTo(statsBombCompetition, source))
    }
}