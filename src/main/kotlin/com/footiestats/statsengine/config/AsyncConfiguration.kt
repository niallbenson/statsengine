package com.footiestats.statsengine.config

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

private val log = KotlinLogging.logger {}

@EnableAsync
class AsyncConfiguration {

    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor? {
        log.debug("Creating Async Task Executor for Events")
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("EventThread-")
        executor.initialize()
        return executor
    }

}