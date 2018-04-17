package de.remk0.twitterbingo.config

import de.remk0.twitterbingo.web.RateLimitChecker
import de.remk0.twitterbingo.web.RateLimitingRequestFilter
import de.remk0.twitterbingo.web.RateLimitingRequestFilterConfiguration
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.ConsumptionProbe
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.*
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableConfigurationProperties(Bucket4JProperties::class)
class Bucket4JConfigurationServletFilter(val properties: Bucket4JProperties) {

    @Bean
    fun bucket4JRequestFilter(): FilterRegistrationBean<RateLimitingRequestFilter> {
        val registration = FilterRegistrationBean<RateLimitingRequestFilter>()
        registration.setName("bucket4JRequestFilter");
        registration.filter = RateLimitingRequestFilter(buildFilterConfigurationAndRateLimitChecker())
        registration.addUrlPatterns(properties.url)
        registration.order = properties.filterOrder
        return registration
    }

    private val bucketMap: MutableMap<String, Bucket> = HashMap()

    private fun buildFilterConfigurationAndRateLimitChecker(): RateLimitingRequestFilterConfiguration {
        val filterConfig = RateLimitingRequestFilterConfiguration()
        filterConfig.url = properties.url
        filterConfig.order = properties.filterOrder
        filterConfig.httpResponseBody = properties.httpResponseBody

        properties.rateLimits.forEach({
            var configBuilder = Bucket4j.builder()
            for (bandWidth in it.bandwidths) {
                configBuilder = configBuilder.addLimit(Bandwidth.simple(bandWidth.capacity, Duration.of(bandWidth.time, bandWidth.unit)))
            }
            val rlc = object : RateLimitChecker {
                override fun probe(request: HttpServletRequest): ConsumptionProbe? {
                    val key = filterConfig.url + "-" + "1"
                    var bucket = bucketMap[key]
                    if (bucket == null) {
                        bucket = configBuilder.build()
                        bucketMap[key] = bucket
                    }
                    return bucket!!.tryConsumeAndReturnRemaining(1)
                }
            }
            filterConfig.rateLimitChecks.add(rlc)
        })

        return filterConfig
    }
}