package de.remk0.twitterbingo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

class RateLimitingSimulation extends Simulation {

  val app: ConfigurableApplicationContext = SpringApplication.run(classOf[TwitterBingoApplication])

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run(): Unit = app.stop()
  })

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling")

  var counter = 0

  val idIterator = Iterator.continually({
    counter += 1
    Map("id" -> counter)
  })

  val scn = scenario("RateLimitingSimulation")
    .feed(idIterator)
    .repeat(6) {
      exec(
        http("Create bingo card")
          .post("/bingoCards")
          .header("Content-Type", "application/json")
          .body(StringBody(
            """
               |{
               |    "id": "${id}",
               |    "size": 3,
               |    "words": [
               |      "upper-left", "upper-middle", "upper-right",
               |      "center-left", "center-middle", "center-right",
               |      "lower-left", "lower-middle", "lower-right"
               |    ]
               |}
            """.stripMargin)).asJSON
          .check(status.in(201))
      )
    }

  setUp(
    scn.inject(atOnceUsers(1))
  )
    .protocols(httpProtocol)
    .assertions(global.successfulRequests.count.lte(5))
}