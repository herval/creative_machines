package hervalicious.unforgiven

import hervalicious.tumblr.Bot


/**
 * Created by herval on 10/31/15.
 */
class BotRunner {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val bot = Bot(
                    Config,
                    LyricsWriter.build(Config)
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
