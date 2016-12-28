package hervalicious.ai.rnn

import hervalicious.util.Logging
import java.util.logging.Logger

/**
 * Created by herval on 3/28/16.
 */
class Trainer(private val network: NetworkManager,
              private val contentLoader: Loader,
              val training: TrainingSet = TrainingSet(),
              val extractor: Extractor = CharacterMapExtractor(network)
) : Logging {
    override val logger = Logger.getGlobal()

    private val trainingSet = contentLoader.iterator(training)

    init {
        // enable when Cuda is installed
//        CudaEnvironment.getInstance().getConfiguration().allowMultiGPU(true)
    }

    fun run() {
        (0..training.iterations).forEach { i ->
            println("Training epoch " + i)
            fit()

            println("Sampling: ")
            println(extractor.sample(training.exampleLength, 1).joinToString("\n"))
            network.save()
        }

        println("All done!")
    }

    fun fit() {
        network.network.model.fit(trainingSet)

        println("Completed epoch")

        trainingSet.reset()    //Reset iterator for another epoch
    }
}
