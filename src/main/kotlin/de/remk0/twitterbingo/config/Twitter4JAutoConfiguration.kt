package de.remk0.twitterbingo.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

@Configuration
@EnableConfigurationProperties(Twitter4JProperties::class)
class Twitter4JAutoConfiguration(val properties: Twitter4JProperties) {

    @Bean
    fun twitterFactory(): TwitterFactory {
        val cb = ConfigurationBuilder()
        cb
                .setOAuthConsumerKey(properties.consumerKey)
                .setOAuthConsumerSecret(properties.consumerSecret)
                .setOAuthAccessToken(properties.accessToken)
                .setOAuthAccessTokenSecret(properties.accessTokenSecret)
        return TwitterFactory(cb.build())
    }

    @Bean
    fun twitter(twitterFactory: TwitterFactory): Twitter {
        return twitterFactory.instance
    }

}