package de.remk0.twitterbingo

import org.junit.jupiter.api.Test

internal class BingoCardTest {

    @Test
    fun emptyBingoCardHasNoBingo() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))

        assert(!bingoCard.isBingo())
    }

    @Test
    fun bingoCardWithEnoughMatchesHasBingo() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))

        bingoCard.match("upper-left")
        assert(!bingoCard.isBingo())

        bingoCard.match("upper-middle")
        bingoCard.match("upper-right")
        assert(bingoCard.isBingo())
    }
}