package de.remk0.twitterbingo

import org.junit.jupiter.api.Test

internal class RegexBingoCardMatcherTest {

    @Test
    fun match() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        val bingoCardMatcher = RegexBingoCardMatcher()
        assert(bingoCardMatcher.match(bingoCard, listOf("upper-left", "upper-middle", "upper-right")))
        assert(bingoCard.isBingo())
    }

    @Test
    fun matchMore() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        val bingoCardMatcher = RegexBingoCardMatcher()
        assert(bingoCardMatcher.match(bingoCard, listOf("upper-left and upper-middle", "upper-right")))
        assert(bingoCard.isBingo())
    }
}