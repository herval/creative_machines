package hervalicious.unforgiven

import hervalicious.ai.rnn.FileLoader
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
                FileLoader(listOf(Config.rawContent), network.characterMap()),
                TrainingSet(
                        iterations = 1000,
                        batchSize = 32,
                        exampleLength = 300,
                        examplesPerIteration = 1600
                )

        ).run()
    }
}