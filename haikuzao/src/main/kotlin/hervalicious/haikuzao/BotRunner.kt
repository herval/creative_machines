package hervalicious.haikuzao

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.FileLoader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.Bot


/**
 * Created by herval on 10/31/15.
 */
class BotRunner {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val bot = Bot(
                    Config,
                    RandomHaikuMaker.build(Config)
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
