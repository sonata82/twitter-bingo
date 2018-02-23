package de.remk0.twitterbingo

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.credential.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.map.repository.config.EnableMapRepositories

@Configuration
@EnableMapRepositories("de.remk0.twitterbingo")
class ApplicationConfiguration {

    @Bean
    fun client() = Client.builder()
            .authenticate(
                    ConsumerKey("XXXXX"), ConsumerSecret("YYYYY"),
                    AccessToken("xxxxx-xxxxx"), AccessTokenSecret("yyyyy")
            )
            .authenticate(BearerToken("ZZZZZ"))  // optional: if you have a Bearer Token
            .connectTimeout(20)  // optional: timeouts in sec
            .readTimeout(40)
            .writeTimeout(20)
            .build()  // return Client instance

}