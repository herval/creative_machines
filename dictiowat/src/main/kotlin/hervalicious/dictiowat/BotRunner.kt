package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.TwitterBot


/**
 * Created by herval on 10/31/15.
 */
class BotRunner {

    companion object {
        @JvmStatic fun main(args: Array<String>) {

            val network = NetworkManager.defaultConfig(
                    Config.networkPath,
                    Config.defaultTopology()
            ).load()

            val bot = TwitterBot(
                    Config,
                    ArticleWriter(Extractor(network))
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
