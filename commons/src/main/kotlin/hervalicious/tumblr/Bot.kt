package hervalicious.tumblr

/**
 * Created by herval on 9/27/16.
 */
class Bot(config: Config, val writer: PostMaker) : Runnable {
    private val client = Client(
            config.consumerToken,
            config.consumerSecret,
            config.blogName,
            config.accessToken,
            config.accessTokenSecret
    )
    private val sleepInterval = config.sleepInterval

    override fun run() {
        while (true) {
            try {
                client.post(
                        writer.title(),
                        writer.content()
                )
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