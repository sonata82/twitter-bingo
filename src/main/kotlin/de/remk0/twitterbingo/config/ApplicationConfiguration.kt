package de.remk0.twitterbingo.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.map.repository.config.EnableMapRepositories

@Configuration
@EnableMapRepositories("de.remk0.twitterbingo")
class ApplicationConfiguration {
}