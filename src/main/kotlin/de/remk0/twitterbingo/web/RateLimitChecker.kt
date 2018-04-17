package de.remk0.twitterbingo.web

import io.github.bucket4j.ConsumptionProbe
import javax.servlet.http.HttpServletRequest

interface RateLimitChecker {
    /**
     * Probes for a rate limit.
     *
     * @param request the http request
     *
     * @return successful probe which describes both result of consumption and tokens remaining in
     * the bucket after consumption or null if no rate limiting should be performed.
     */
    fun probe(request: HttpServletRequest): ConsumptionProbe?
}