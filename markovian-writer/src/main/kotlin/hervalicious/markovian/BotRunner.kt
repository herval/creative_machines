package hervalicious.markovian

import hervalicious.markovian.text.Writer
import hervalicious.twitter.Bot


/**
 * Created by herval on 10/31/15.
 */
class BotRunner {

    companion object {
        @JvmStatic fun main(args: Array<String>) {

            val bot = Bot(
                    Config,
                    Writer()
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
