package hervalicious.ai.rnn

import com.google.common.base.Joiner
import hervalicious.ai.rnn.*
import java.io.File
import java.io.IOException
import java.nio.file.Path


/**
 * Created by herval on 3/28/16.
 */
class Trainer(private val network: NetworkManager, private val contentLoader: Loader, val training: TrainingSet = TrainingSet()) {
    private val trainingSet = contentLoader.iterator(training)
    private val extractor =  Extractor(network)

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
