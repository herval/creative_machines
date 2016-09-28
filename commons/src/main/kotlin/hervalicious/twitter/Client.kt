package hervalicious.twitter

import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

/**
 * Created by herval on 9/27/16.
 */
class Client(consumerKey: String, consumerSecret: String, accessToken: String, accessTokenSecret: String) {

    val config = ConfigurationBuilder()

    init {
        config.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
    }

    val factory = TwitterFactory(config.build())
    val twitter = factory.getInstance()

    fun post(message: String) {
        twitter.updateStatus(message)
    }
}
