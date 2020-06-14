package com.footiestats.statsengine.config

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

private val log = KotlinLogging.logger {}

@Configuration
@EnableAsync(proxyTargetClass = true)
class AsyncConfiguration {

    @Bean(name = ["threadPoolTaskExecutor"])
    fun threadPoolTaskExecutor(): TaskExecutor? {
        log.debug("Creating Async Task Executor for Events")
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 10
        executor.setQueueCapacity(1000)
        executor.setThreadNamePrefix("EventThread-")
        executor.initialize()
        return executor
    }

}