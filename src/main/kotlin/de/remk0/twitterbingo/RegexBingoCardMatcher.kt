package de.remk0.twitterbingo

import org.apache.tomcat.util.buf.StringUtils
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class RegexBingoCardMatcher : BingoCardMatcher {
    override fun match(bingoCard: BingoCard, text: Iterable<String>): Boolean {
        // initialize pattern
        val patternString = "(?i).*(" + StringUtils.join(bingoCard.words, '|') + ").*"
        val pattern = Pattern.compile(patternString)

        // match
        text.forEach({
            val matcher = pattern.matcher(it)

            while(matcher.find()) {
                if (bingoCard.match(matcher.group(1))) {
                    return true
                }
            }
        })
        return false
    }
}