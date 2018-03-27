package de.remk0.twitterbingo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("twitter4j")
data class Twitter4JProperties (var consumerKey: String?, var consumerSecret: String?, var accessToken: String?, var accessTokenSecret: String?)