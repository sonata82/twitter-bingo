package de.remk0.twitterbingo

import org.springframework.data.annotation.Id;

data class BingoCard(@Id val id: String, private val size: Int, val words: List<String>) {
    private val matched = Array(size, { BooleanArray(size) })

    fun match(word: String?): Boolean {

        val index = words.indexOfFirst { it.equals(word, true) }

        val row = index / size
        val column = index % size

        matched[row][column] = true

        return isBingo()
    }

    private var bingo: Boolean = false

    fun isBingo(): Boolean {
        if (bingo) {
            return true
        }

        var d1 = true
        var d2 = true
        for (i in 0 until size) {
            if (matched[i].all { it }) {
                bingo = true
                return true
            }
            var b = true;
            for (j in 0 until size) {
                b = b && matched[j][i]
            }
            if (b) {
                bingo = true
                return true
            }
            d1 = d1 && matched[i][i]
            d2 = d2 && matched[i][size-i-1]
        }
        if (d1 || d2) {
            bingo = true
            return true
        }

        return false
    }
}