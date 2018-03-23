package de.remk0.twitterbingo.web

import de.remk0.twitterbingo.BingoCardMatcher
import de.remk0.twitterbingo.BingoCardRepository
import de.remk0.twitterbingo.TwitterClient

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PlayController(val bingoCardRepository: BingoCardRepository, val twitterClient: TwitterClient, val bingoCardMatcher: BingoCardMatcher) {

    @GetMapping("/play")
    fun play(
            @RequestParam(value = "bingoCardId") bingoCardId: String,
            @RequestParam(value = "twitterUserId") userId: Long
    ): ResponseEntity<*> {
        val bingoCard = bingoCardRepository.findById(bingoCardId).get()
        val userTimeline = twitterClient.read(userId)

        return if (bingoCardMatcher.match(bingoCard, userTimeline)) ok().build<String>() else notFound().build<String>()
    }

}