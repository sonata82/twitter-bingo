package de.remk0.twitterbingo

interface TwitterClient {
    fun read(userId: Long) : Iterable<String>
}