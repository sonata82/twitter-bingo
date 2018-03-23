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

        for (i in 0 until size) {
            if (matched[i].all { it }) {
                bingo = true
                return true
            }
        }
        return false
    }
}