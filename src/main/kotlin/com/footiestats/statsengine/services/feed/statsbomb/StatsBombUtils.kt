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
        fun convertToDate(date: String, time: String): Date =
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss").parse("$date $time")

        fun convertToDateFromShort(date: String?): LocalDateTime? =
                if (date != null) LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE) else null

        fun convertToDateFromLong(date: String?): LocalDateTime? =
                if (date != null) LocalDateTime.parse(date) else null

    }
}