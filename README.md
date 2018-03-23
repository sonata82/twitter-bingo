# Twitter Bingo

A server that enables playing [Buzzword Bingo](https://en.wikipedia.org/wiki/Buzzword_bingo) with the Timeline of a 
someone's Twitter account.

## Usage
Add your Twitter OAuth credentials to the `application.properties` file:

    twitter4j.consumerKey=
    twitter4j.consumerSecret=
    twitter4j.accessToken=
    twitter4j.accessTokenSecret=

Start the application and create a bingo card by POSTing to the `bingoCards` endpoint:

    curl -H "Content-Type: application/json" -X POST -d '{"id":"myFirstBingoCard","size":"3","words":["upper-left", "upper-middle", "upper-right", "center-left", "center-middle", "center-right", "lower-left", "lower-middle", "lower-right"]}' http://localhost:8080/bingoCards

Now start playing by entering someone's Twitter user id:

    curl http://localhost:8080/play?bingoCardId=myFirstBingoCard\&twitterUserId= 