package com.footiestats.statsengine

import com.footiestats.statsengine.services.engine.SourceService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration(private val sourceService: SourceService) {

    @Bean
    fun databaseInitializer() = ApplicationRunner {
        sourceService.seed()
    }
}
