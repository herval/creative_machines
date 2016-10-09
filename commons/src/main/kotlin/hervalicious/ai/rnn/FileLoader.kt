package hervalicious.ai.rnn

import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Build a char iterator based on a training set and a bunch of files
 *
 * Created by herval on 3/28/16.
 */
class FileLoader(files: List<File>, private val characterMap: CharacterMap) : Loader {

    val contents = files.flatMap { f -> FileUtils.readLines(f) }

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