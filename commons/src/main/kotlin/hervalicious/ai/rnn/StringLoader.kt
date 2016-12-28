package hervalicious.ai.rnn

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator

/**
 * Created by herval on 3/28/16.
 */
class StringLoader(private val contents: List<String>, private val characterMap: CharacterMap) : Loader {

    override fun iterator(trainingSet: TrainingSet): DataSetIterator {
        return CharacterIterator(
                contents,
                trainingSet.batchSize,
                trainingSet.exampleLength,
                trainingSet.examplesPerIteration,
                characterMap,
                true
        )
    }
}
