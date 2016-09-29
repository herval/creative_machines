package hervalicious.haikuzao

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.Loader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.TwitterBot


/**
 * Created by herval on 10/31/15.
 */
class BotRunner {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val bot = TwitterBot(
                    Config,
                    RandomHaikuMaker.build(Config)
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
