package hervalicious.ai.rnn

/**
 * Created by herval on 10/9/16.
 */
interface Loader {
    fun iterator(trainingSet: TrainingSet): CharacterIterator
}