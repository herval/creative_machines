package hervalicious.haikuzao

import hervalicious.ai.rnn.Loader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.ai.rnn.Trainer

/**
 * Created by herval on 9/25/16.
 */
object BotTrainer {
    @JvmStatic fun main(args: Array<String>) {
        val network = NetworkManager.defaultConfig(
                Config.networkPath,
                Config.defaultTopology(Config.defaultCharacterMap)
        )

        Trainer(
                network,
                Loader(listOf(Config.rawContent), network.characterMap())
        ).run()
    }
}