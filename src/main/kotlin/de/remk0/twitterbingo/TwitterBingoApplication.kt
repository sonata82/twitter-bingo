package de.remk0.twitterbingo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwitterBingoApplication

fun main(args: Array<String>) {
    runApplication<TwitterBingoApplication>(*args)
}
