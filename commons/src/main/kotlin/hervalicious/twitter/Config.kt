package hervalicious.twitter

/**
 * Created by herval on 9/27/16.
 */
abstract class Config {
    val env = hervalicious.util.Env()

    val accessToken: String by lazy { env.get("TWITTER_ACCESS_TOKEN") }
    val accessTokenSecret: String by lazy { env.get("TWITTER_ACCESS_TOKEN_SECRET") }
    val consumerSecret: String by lazy { env.get("TWITTER_CONSUMER_SECRET") }
    val consumerToken: String by lazy { env.get("TWITTER_CONSUMER_TOKEN") }
    val sleepInterval = 60 * 60 * 1000L
}