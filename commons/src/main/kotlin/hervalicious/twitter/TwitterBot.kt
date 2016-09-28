package hervalicious.twitter

/**
 * Created by herval on 9/27/16.
 */
class TwitterBot(config: Config, val tweeter: TweetMaker) : Runnable {
    private val client = Client(config.consumerToken, config.consumerSecret, config.accessToken, config.accessTokenSecret)
    private val sleepInterval = config.sleepInterval

    override fun run() {
        while (true) {
            try {
                val content = tweeter.sample()
                println(content)
                client.post(content)
            } catch (e: Exception) {
                println("Couldn't post at this time, will retry after the break.")
                e.printStackTrace()
                // TODO handle it
            }
            try {
                Thread.sleep(sleepInterval)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                // what can one ever do about this exception anyway?
            }

        }
    }
}