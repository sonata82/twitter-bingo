package de.remk0.twitterbingo.web

import de.remk0.twitterbingo.BingoCardRepository
import de.remk0.twitterbingo.TwitterClient
import org.apache.tomcat.util.buf.StringUtils
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.regex.Pattern

@Controller
class PlayController(val bingoCardRepository: BingoCardRepository, val twitterClient: TwitterClient) {

    @GetMapping("/play")
    fun play(
            @RequestParam(value = "bingoCardId") bingoCardId: String,
            @RequestParam(value = "twitterUserId") userId: Long
    ): ResponseEntity<*> {
        // get bingo card
        val bingoCard = bingoCardRepository.findById(bingoCardId).get()
        // get twitter timeline
        val userTimeline = twitterClient.read(userId)
        // initialize pattern
        val patternString = "(?i)\\b(" + StringUtils.join(bingoCard.words, '|') + ")\\b"
        val pattern = Pattern.compile(patternString)

        // match
        userTimeline.forEach({
            val matcher = pattern.matcher(it)

            while(matcher.find()) {
                if (bingoCard.match(matcher.group(1))) {
                    return ok().build<String>()
                }
            }
        })
        return notFound().build<String>()
    }

}