package com.footiestats.statsengine.services.feed.statsbomb

import org.springframework.format.annotation.DateTimeFormat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StatsBombUtils {
    companion object {
        fun convertToDate(date: String, time: String): LocalDateTime =
                LocalDateTime.parse("${date}T${time}Z")

        fun convertToDateFromShort(date: String?): LocalDate? =
                if (date != null) LocalDate.parse(date, DateTimeFormatter.ISO_DATE) else null

        fun convertToDateFromLong(date: String?): LocalDateTime? =
                if (date != null) LocalDateTime.parse(date) else null

    }
}