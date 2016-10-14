package hervalicious.dictiowat

import hervalicious.ai.rnn.NetworkManager
import hervalicious.ai.rnn.StringLoader
import hervalicious.ai.rnn.Trainer
import hervalicious.ai.rnn.TrainingSet

/**
 * Created by herval on 9/25/16.
 */
object BotTrainer {
    @JvmStatic fun main(args: Array<String>) {
        val config = Config()
        val dictionary = Loader(config.jsonContent)

        val procs = listOf(
                Thread(Runnable {
                    val network = NetworkManager.loadOrDefault(
                            config.wordsNetworkPath,
                            config.wordsTopology()
                    )
                    Trainer(
                            network,
                            StringLoader(dictionary.words, network.characterMap()),
                            TrainingSet(
                                    iterations = 100,
                                    batchSize = 5,
                                    exampleLength = 50,
                                    examplesPerIteration = 50
                            )

                    ).run()
                }),
                Thread(Runnable {
                    val network = NetworkManager.loadOrDefault(
                            config.definitionsNetworkPath,
                            config.definitionsTopology()
                    )
                    Trainer(
                            network,
                            StringLoader(dictionary.definitions, network.characterMap()),
                            TrainingSet(
                                    iterations = 100,
                                    batchSize = 5,
                                    exampleLength = 50,
                                    examplesPerIteration = 300
                            )
                    ).run()
                })
        )

        procs.forEach(Thread::start)
        procs.forEach(Thread::join)
    }
}