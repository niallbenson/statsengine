package com.footiestats.statsengine

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("statsengine")
data class StatsengineProperties(var title: String, val banner: Banner) {
    data class Banner(val title: String? = null, val content: String)
}