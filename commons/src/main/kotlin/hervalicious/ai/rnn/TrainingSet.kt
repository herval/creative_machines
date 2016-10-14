package hervalicious.ai.rnn

/**
 * Created by herval on 9/25/16.
 */
class TrainingSet(
        val iterations: Int = 100,
        val batchSize: Int = 5,
        val exampleLength: Int = 50,
        val examplesPerIteration: Int = 300
)