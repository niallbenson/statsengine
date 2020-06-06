package com.footiestats.statsengine.dtos.engine

data class CompetitionDTO(
    val id: Long,
    val name: String,
    val gender: String,
    var country: String,
    val countryFlag: String,
    val seasons: Array<SeasonDTO>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompetitionDTO

        if (id != other.id) return false
        if (name != other.name) return false
        if (gender != other.gender) return false
        if (country != other.country) return false
        if (countryFlag != other.countryFlag) return false
        if (!seasons.contentEquals(other.seasons)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + countryFlag.hashCode()
        result = 31 * result + seasons.contentHashCode()
        return result
    }
}