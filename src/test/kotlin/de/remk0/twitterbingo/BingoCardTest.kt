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

    @Test
    fun bingoCardWithMatchesVerticalHasBingo() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        bingoCard.match("upper-middle")
        bingoCard.match("center-middle")
        bingoCard.match("lower-middle")
        assert(bingoCard.isBingo())
    }

    @Test
    fun bingoCardWithMatchesDiagonalHasBingo() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        bingoCard.match("upper-left")
        bingoCard.match("center-middle")
        bingoCard.match("lower-right")
        assert(bingoCard.isBingo())
    }

    @Test
    fun bingoCardWithMatchesDiagonal2HasBingo() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        bingoCard.match("upper-right")
        bingoCard.match("center-middle")
        bingoCard.match("lower-left")
        assert(bingoCard.isBingo())
    }
}