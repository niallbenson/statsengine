package com.footiestats.statsengine.services.feed.statsbomb.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatsBombDateUtils {
    companion object {
        fun convertToDate(date: String, time: String?): LocalDateTime {
            return if (time != null)
                LocalDateTime.parse("${date}T${time}")
            else LocalDateTime.parse("${date}T00:00:00.000")
        }

        fun convertToDateFromShort(date: String?): LocalDate? =
                if (date != null) LocalDate.parse(date, DateTimeFormatter.ISO_DATE) else null

        fun convertToDateFromLong(date: String?): LocalDateTime? =
                if (date != null) LocalDateTime.parse(date) else null

    }
}