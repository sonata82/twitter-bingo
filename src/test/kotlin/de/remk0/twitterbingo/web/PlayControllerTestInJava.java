package de.remk0.twitterbingo.web;

import de.remk0.twitterbingo.BingoCard;
import de.remk0.twitterbingo.BingoCardRepository;
import de.remk0.twitterbingo.TwitterClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayControllerTestInJava {

    @MockBean
    private BingoCardRepository bingoCardRepository;

    @MockBean
    private TwitterClient twitterClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void play() {
        BingoCard bingoCard = new BingoCard("test", 3, Arrays.asList("upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"));
        given(bingoCardRepository.findById("1")).willReturn(Optional.of(bingoCard));
        ArrayList<String> feed = new ArrayList<>();
        feed.add("upper-left");
        feed.add("upper-middle");
        feed.add("upper-right");
        given(twitterClient.read(2L)).willReturn(feed);

        URI uri = UriComponentsBuilder
                .fromUriString("/play")
                .queryParam("bingoCardId", "1")
                .queryParam("twitterUserId", "2")
                .build().toUri();
        HttpStatus statusCode = this.restTemplate.getForEntity(uri, String.class).getStatusCode();
        assertTrue(statusCode.is2xxSuccessful());
    }
}

