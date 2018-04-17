package de.remk0.twitterbingo.web

import io.github.bucket4j.ConsumptionProbe
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class RateLimitingRequestFilterConfiguration(
        var url: String? = null,
        var order: Int = 0,
        var httpResponseBody: String? = null,
        var rateLimitChecks: MutableList<RateLimitChecker> = ArrayList()
)

class RateLimitingRequestFilter(private val filterConfiguration: RateLimitingRequestFilterConfiguration): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        var allConsumed = true
        var remainingLimit: Long? = null

        for (rl: RateLimitChecker in filterConfiguration.rateLimitChecks) {
            val probe: ConsumptionProbe? = rl.probe(request)
            if (probe != null) {
                if (probe.isConsumed) {
                    remainingLimit = getLowestRemainingLimit(remainingLimit, probe);
                } else {
                    allConsumed = false;
                    handleHttpResponseOnRateLimiting(response, probe);
                    break
                }
                break
            }
        }
        if (allConsumed) {
            if (remainingLimit != null) {
                response.setHeader("X-Rate-Limit-Remaining", remainingLimit.toString());
            }
            filterChain.doFilter(request, response);
        }
    }

    private fun getLowestRemainingLimit(currentRemaining: Long?, consumptionProbe: ConsumptionProbe?): Long? {
        var lowestRemaining = currentRemaining
        if (consumptionProbe != null) {
            if (lowestRemaining == null) {
                lowestRemaining = consumptionProbe.remainingTokens
            } else if (consumptionProbe.remainingTokens < lowestRemaining) {
                lowestRemaining = consumptionProbe.remainingTokens
            }
        }
        return lowestRemaining
    }

    @Throws(IOException::class)
    private fun handleHttpResponseOnRateLimiting(httpResponse: HttpServletResponse, consumptionProbe: ConsumptionProbe) {
        httpResponse.status = 429
        httpResponse.setHeader("X-Rate-Limit-Retry-After-Seconds", "" + TimeUnit.NANOSECONDS.toSeconds(consumptionProbe.nanosToWaitForRefill))
        httpResponse.contentType = "application/json"
        httpResponse.characterEncoding = "UTF-8"
        httpResponse.writer.append(filterConfiguration.httpResponseBody)
    }
}