package de.remk0.twitterbingo.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class Bucket4JConfigurationServletFilterTest {

    @Autowired
    var environment: Environment? = null

    @Test
    fun contextLoads() {
        assert(environment!!.getProperty("bucket4j.rate-limits[0].bandwidths[0].time").equals("10"))
    }
}