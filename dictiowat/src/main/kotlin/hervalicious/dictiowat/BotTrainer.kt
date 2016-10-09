package hervalicious.dictiowat

import hervalicious.ai.rnn.NetworkManager
import hervalicious.ai.rnn.StringLoader
import hervalicious.ai.rnn.Trainer

/**
 * Created by herval on 9/25/16.
 */
object BotTrainer {
    @JvmStatic fun main(args: Array<String>) {
        val config = Config()
        val dictionary = Loader(config.jsonContent)

        val procs = listOf(
                Thread(Runnable {
                    val network = NetworkManager.defaultConfig(
                            config.wordsNetworkPath,
                            config.wordsTopology()
                    )
                    Trainer(
                            network,
                            StringLoader(dictionary.words, network.characterMap())
                    ).run()
                }),
                Thread(Runnable {
                    val network = NetworkManager.defaultConfig(
                            config.definitionsNetworkPath,
                            config.definitionsTopology()
                    )
                    Trainer(
                            network,
                            StringLoader(dictionary.definitions, network.characterMap())
                    ).run()
                })
        )

        procs.forEach(Thread::start)
        procs.forEach(Thread::join)
    }
}