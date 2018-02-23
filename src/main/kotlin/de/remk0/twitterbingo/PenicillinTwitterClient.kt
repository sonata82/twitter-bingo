package de.remk0.twitterbingo

import jp.nephy.penicillin.Client
import org.springframework.stereotype.Service

@Service
class PenicillinTwitterClient(val client: Client) : TwitterClient  {
    override fun read(userId: Long): Iterable<String> {
        return client.status.getUserTimeline(userId, null, null, null, null, true, true, false)
                .map { it.text }
    }
}