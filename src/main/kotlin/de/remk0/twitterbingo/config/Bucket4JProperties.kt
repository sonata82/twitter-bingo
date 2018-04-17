package de.remk0.twitterbingo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.temporal.ChronoUnit

@ConfigurationProperties("bucket4j")
data class Bucket4JProperties (
        var enabled: Boolean = false,
        var url: String = "/*",
        var filterOrder: Int = Integer.MIN_VALUE + 1,
        var rateLimits: List<RateLimit> = ArrayList(),
        var httpResponseBody: String = "{ \"message\": \"This endpoint is rate limited.\" }"
)

data class RateLimit (
        var bandwidths: List<BandWidth> = java.util.ArrayList()
)

data class BandWidth (
        var capacity: Long = 0,
        var time: Long = 0,
        var unit: ChronoUnit? = null
)