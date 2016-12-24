package hervalicious.ai.rnn

import com.google.common.base.Joiner


/**
 * Created by herval on 3/28/16.
 */
class Trainer(private val network: NetworkManager, private val contentLoader: Loader, val training: TrainingSet = TrainingSet()) {
    private val trainingSet = contentLoader.iterator(training)
    private val extractor =  Extractor(network)

    init {
        // enable when Cuda is installed
//        CudaEnvironment.getInstance().getConfiguration().allowMultiGPU(true)
    }

    fun run() {
        (0..training.iterations).forEach { i ->
            println("Training epoch " + i)
            fit()

            println("Sampling: ")
            println(Joiner.on("\n").join(extractor.sample(training.exampleLength, 1)))
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
