package com.footiestats.statsengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(StatsengineProperties::class)
class StatsengineApplication

fun main(args: Array<String>) {
	runApplication<StatsengineApplication>(*args)
}