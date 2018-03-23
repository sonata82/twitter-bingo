package de.remk0.twitterbingo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("twitter4j")
class Twitter4JProperties {

    var consumerKey: String? = null
    var consumerSecret: String? = null
    var accessToken: String? = null
    var accessTokenSecret: String? = null

}