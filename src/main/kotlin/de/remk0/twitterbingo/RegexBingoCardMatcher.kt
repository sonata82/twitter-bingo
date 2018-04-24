package de.remk0.twitterbingo

import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class RegexBingoCardMatcher : BingoCardMatcher {
    override fun match(bingoCard: BingoCard, text: Iterable<String>): Boolean {
        // initialize pattern
        val patternString = bingoCard.words.joinToString(")|(", "(?i).*(", ").*")
        val pattern = Pattern.compile(patternString)

        // match
        text.forEach({
            val matcher = pattern.matcher(it)

            while (matcher.find()) {
                (1..matcher.groupCount())
                        .mapNotNull { matcher.group(it) }
                        .forEach { bingoCard.match(it) }
            }
        })
        return bingoCard.isBingo()
    }
}