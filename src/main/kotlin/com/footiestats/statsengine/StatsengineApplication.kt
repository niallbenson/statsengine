package com.footiestats.statsengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatsengineApplication

fun main(args: Array<String>) {
	runApplication<StatsengineApplication>(*args)
}