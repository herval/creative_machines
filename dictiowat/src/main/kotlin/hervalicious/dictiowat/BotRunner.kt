package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.Bot


/**
 * Created by herval on 10/31/15.
 */
object BotRunner {

    @JvmStatic fun main(args: Array<String>) {
        val config = Config()

        val bot = Bot(
                config,
                ArticleWriter.build(config)
        )

        val proc = Thread(bot)
        proc.run()
        proc.join()
    }
}
