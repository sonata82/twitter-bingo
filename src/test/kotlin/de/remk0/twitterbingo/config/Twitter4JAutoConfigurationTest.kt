package de.remk0.twitterbingo.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension
import twitter4j.Twitter

@ExtendWith(SpringExtension::class)
@SpringBootTest
class Twitter4JAutoConfigurationTest {

    @Autowired
    var environment: Environment? = null

    @Autowired
    var twitter: Twitter? = null

    @Test
    fun contextLoads() {
        assert(environment!!.getProperty("twitter4j.accessTokenSecret").equals("4"))
        assert(twitter!!.configuration.oAuthAccessTokenSecret.equals("4"))
    }

}
