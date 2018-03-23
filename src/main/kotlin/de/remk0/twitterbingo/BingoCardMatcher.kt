package de.remk0.twitterbingo

interface BingoCardMatcher {
    fun match(bingoCard: BingoCard, text: Iterable<String>): Boolean
}