package hervalicious.twitter

/**
 * Created by herval on 9/27/16.
 */
interface Config {
    val consumerToken: String
    val consumerSecret: String
    val accessToken: String
    val accessTokenSecret: String
    val sleepInterval: Long
}