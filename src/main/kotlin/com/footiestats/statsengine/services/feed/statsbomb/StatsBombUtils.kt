package com.footiestats.statsengine.services.feed.statsbomb

import java.text.SimpleDateFormat
import java.util.*

class StatsBombUtils {
    companion object {
        fun convertToDate(date: String, time: String): Date =
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss").parse("$date $time")
    }
}