package hervalicious.dictiowat

import hervalicious.ai.rnn.Loader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.ai.rnn.Trainer

/**
 * Created by herval on 9/25/16.
 */
object BotTrainer {
    @JvmStatic fun main(args: Array<String>) {
        val network = NetworkManager.defaultConfig(
                hervalicious.dictiowat.Config.networkPath,
                hervalicious.dictiowat.Config.defaultTopology(hervalicious.dictiowat.Config.defaultCharacterMap)
        )

        Trainer(
                network,
                Loader(listOf(Config.rawContent.toFile()), network.characterMap())
        ).run()
    }
}