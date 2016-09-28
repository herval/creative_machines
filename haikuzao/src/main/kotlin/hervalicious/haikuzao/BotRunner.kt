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

            val network = NetworkManager.defaultConfig(
                    Config.networkPath,
                    Config.defaultTopology()
            ).load()

            val data = Loader(listOf(Config.rawContent.toFile()), network.characterMap()).contents
            val dictionary = data.map { l -> l.split(" ") }.flatten().map { w -> w.toLowerCase() }.toSet()


            val bot = TwitterBot(
                    Config,
                    RandomHaikuMaker(Extractor(network), dictionary)
            )

            val proc = Thread(bot)
            proc.run()
            proc.join()
        }
    }
}
