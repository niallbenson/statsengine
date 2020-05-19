package com.footiestats.statsengine.services.feed.statsbomb.utils

import java.io.File

class StatsBombFileUtils {
    companion object {
        fun readFile(filePath: String): String {
            var text = ""
            File(filePath).inputStream().use {
                val bytes = it.readBytes()
                text = String(bytes)
            }
            return text
        }
    }
}