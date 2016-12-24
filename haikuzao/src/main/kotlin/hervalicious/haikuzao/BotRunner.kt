package hervalicious.haikuzao

import hervalicious.twitter.Bot


/**
 * Created by herval on 10/31/15.
 */
object BotRunner {

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