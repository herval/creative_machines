package hervalicious.ai.rnn

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator

/**
 * Created by herval on 10/9/16.
 */
interface Loader {
    fun iterator(trainingSet: TrainingSet): DataSetIterator
}