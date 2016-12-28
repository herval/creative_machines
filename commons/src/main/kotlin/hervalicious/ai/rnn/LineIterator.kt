package hervalicious.ai.rnn

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.factory.Nd4j
import java.util.*

/**
 * An iterator that feeds a batch of entire lines at a time
 */
class LineIterator(
        private val lines: List<String>,
        private val miniBatchSize: Int,
        private val exampleLength: Int,
        private val numExamplesToFetch: Int,
        private val fixedLineWidth: Int
) : DataSetIterator {

    private var examplesSoFar = 0
    private val rng = Random()

    init {
        if (numExamplesToFetch % miniBatchSize != 0) {
            throw IllegalArgumentException("numExamplesToFetch must be a multiple of miniBatchSize")
        }
        if (miniBatchSize <= 0) {
            throw IllegalArgumentException("Invalid miniBatchSize (must be >0)")
        }
    }

//    fun convertIndexToCharacter(idx: Int): Char {
//        return this.characterMap.charAt(idx)
//    }
//
//    fun convertCharacterToIndex(c: Char): Int {
//        return this.characterMap.indexOf(c)!!
//    }

//    val randomCharacter: Char
//        get() = characterMap.sampleChar()

    override fun hasNext(): Boolean {
        return examplesSoFar + miniBatchSize <= numExamplesToFetch
    }

    override fun next(): DataSet {
        return next(miniBatchSize)
    }

    override fun next(num: Int): DataSet {
        if (examplesSoFar + num > numExamplesToFetch) throw NoSuchElementException()
        //Allocate space:

        val input = Nd4j.zeros(num, fixedLineWidth)
        val labels = Nd4j.zeros(num, fixedLineWidth)

        //Randomly select a subset of the file. No attempt is made to avoid overlapping subsets
        // of the file in the same minibatch
        for (i in 0..(num - 1)) {
            val line = lines.get(rng.nextInt(lines.size-1))
//            println(line)

            // make all tweets 140-char long
            val padded = if(line.length < fixedLineWidth) {
                line.padEnd(fixedLineWidth)
            } else {
                line.substring(0..(fixedLineWidth-1))
            }

//            println(padded.length)

            padded.forEachIndexed { j, c ->
                if(j < padded.length-1) {
                    val currCharIdx = c.toInt()
                    val nextCharIdx = padded.get(j + 1).toInt()

//                    print(currCharIdx.toChar())

                    input.put(i, j, currCharIdx)
                    labels.put(i, j, nextCharIdx)
                }
            }
//            println("")
        }

        examplesSoFar += num
        return DataSet(input, labels)
    }

    override fun totalExamples(): Int {
        return numExamplesToFetch
    }

    override fun inputColumns(): Int {
        return fixedLineWidth
    }

    override fun totalOutcomes(): Int {
        return fixedLineWidth// Int.MAX_VALUE
    }

    override fun reset() {
        examplesSoFar = 0
    }

    override fun batch(): Int {
        return miniBatchSize
    }

    override fun cursor(): Int {
        return examplesSoFar
    }

    override fun numExamples(): Int {
        return numExamplesToFetch
    }

    override fun resetSupported(): Boolean {
        return false
    }

    override fun setPreProcessor(preProcessor: DataSetPreProcessor) {
        // noop
    }

    override fun remove() {
        throw UnsupportedOperationException()
    }

    override fun asyncSupported(): Boolean {
        return true
    }

    override fun getLabels(): MutableList<String> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.    }
    }

    override fun getPreProcessor(): DataSetPreProcessor {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}