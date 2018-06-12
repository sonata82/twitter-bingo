package de.remk0.twitterbingo.web

import de.remk0.twitterbingo.BingoCard
import de.remk0.twitterbingo.BingoCardRepository
import de.remk0.twitterbingo.TwitterClient
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Extensions(ExtendWith(SpringExtension::class), ExtendWith(MockitoExtension::class))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TestPlayControllerInKotlin(
        @Autowired private val restTemplate : TestRestTemplate) {

    @MockBean
    lateinit var bingoCardRepository: BingoCardRepository

    @MockBean
    lateinit var twitterClient: TwitterClient

    @Test
    fun play() {
        val bingoCard = BingoCard("test", 3, listOf("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"))
        given(bingoCardRepository.findById("test")).willReturn(Optional.of(bingoCard))
        given(twitterClient.read(2L)).willReturn(listOf("somewhere over the upper-left corner", "how about some upper-Middle?", "where are going upper-right"))

        val uri = UriComponentsBuilder
                .fromUriString("/play")
                .queryParam("bingoCardId", "test")
                .queryParam("twitterUserId", "2")
                .build().toUri()
        val statusCode = this.restTemplate.getForEntity<String>(uri).statusCode
        assert(statusCode.is2xxSuccessful)
    }

}