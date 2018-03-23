package de.remk0.twitterbingo

import org.springframework.stereotype.Service
import twitter4j.Twitter

@Service
class Twitter4JTwitterClient(private val twitter: Twitter) : TwitterClient {

    override fun read(userId: Long): Iterable<String> {
        return twitter.getUserTimeline(userId).map { it.text };
    }

}