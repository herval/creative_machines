package hervalicious.deepherval

import hervalicious.ai.rnn.FixedWidthUnicodeExtractor
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

        val TWEET_SIZE = 140 // tweets have a fixed size (which we use as the input dimensionality here)

        // TODO try a different sampling mechanism? (eg train w/ entire tweets instead of randomized)
        Trainer(
                network,
                CsvLoader(Config.tweets),
                TrainingSet(
                        iterations = 1000,
                        batchSize = 10,
                        exampleLength = TWEET_SIZE,
                        examplesPerIteration = 100
                ),
                FixedWidthUnicodeExtractor(network, TWEET_SIZE)
        ).run()
    }
}