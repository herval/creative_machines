package hervalicious.ai.rnn

/**
 * Created by herval on 3/28/16.
 */
class StringLoader(private val contents: List<String>, private val characterMap: CharacterMap) : Loader {

    override fun iterator(trainingSet: TrainingSet): CharacterIterator {
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
