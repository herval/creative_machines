package hervalicious.twitter

/**
 * Created by herval on 9/27/16.
 */
abstract class Config {
    val conf = hervalicious.util.Config()

    val accessToken: String by lazy { conf.get("TWITTER_ACCESS_TOKEN") }
    val accessTokenSecret: String by lazy { conf.get("TWITTER_ACCESS_TOKEN_SECRET") }
    val consumerSecret: String by lazy { conf.get("TWITTER_CONSUMER_SECRET") }
    val consumerToken: String by lazy { conf.get("TWITTER_CONSUMER_TOKEN") }
    val sleepInterval = 60 * 60 * 1000L
}