package hervalicious.haikuzao

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
                Config.networkPath,
                Config.defaultTopology(Config.defaultCharacterMap)
        )

        Trainer(
                network,
                FileLoader(listOf(Config.rawContent), network.characterMap()),
                TrainingSet(
                        iterations = 100,
                        batchSize = 5,
                        exampleLength = 50,
                        examplesPerIteration = 300
                )
        ).run()
    }
}