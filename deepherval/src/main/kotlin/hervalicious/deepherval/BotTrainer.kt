package hervalicious.deepherval

import hervalicious.ai.rnn.NetworkManager
import hervalicious.ai.rnn.Trainer
import hervalicious.ai.rnn.TrainingSet

/**
 * Created by herval on 9/25/16.
 */
object BotTrainer {
    @JvmStatic fun main(args: Array<String>) {
        val network = NetworkManager.defaultConfig(
                Config.networkFile,
                Config.defaultTopology
        )

        Trainer(
                network,
                CsvLoader(Config.tweets, network.characterMap()),
                TrainingSet(
                        iterations = 1000,
                        batchSize = 10,
                        exampleLength = 140,
                        examplesPerIteration = 100
                )
        ).run()
    }
}