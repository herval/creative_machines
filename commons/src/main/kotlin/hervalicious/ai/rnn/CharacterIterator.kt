package hervalicious.ai.rnn

import java.io.IOException
import java.util.Arrays
import java.util.NoSuchElementException
import java.util.Random

import org.deeplearning4j.datasets.iterator.DataSetIterator
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.factory.Nd4j

import hervalicious.ai.rnn.CharacterMap

/**
 * A very simple DataSetIterator for use in the GravesLSTMCharModellingExample.
 * Given a text file and a few options, generate feature vectors and labels for training,
 * where we want to predict the next character in the sequence.
 * This is done by randomly choosing a position in the text file to start the sequence and
 * (optionally) scanning backwards to a new line (to ensure we don't start half way through a word
 * for example).
 * Feature vectors and labels are both one-hot vectors of same length

 * Based on code by Alex Black
 */
class CharacterIterator(
        lines: List<String>,
        private val miniBatchSize: Int,
        private val exampleLength: Int,
        private val numExamplesToFetch: Int,
        private val characterMap: CharacterMap,
        private val alwaysStartAtNewLine: Boolean
) : DataSetIterator {

    private val MAX_SCAN_LENGTH = 200
    private var fileCharacters: CharArray? = null
    private var examplesSoFar = 0
    private val rng = Random()

    init {
        if (numExamplesToFetch % miniBatchSize != 0) {
            throw IllegalArgumentException("numExamplesToFetch must be a multiple of miniBatchSize")
        }
        if (miniBatchSize <= 0) {
            throw IllegalArgumentException("Invalid miniBatchSize (must be >0)")
        }


        //Load file and convert contents to a char[]
        val newLineValid = this.characterMap.contains('\n')
        var maxSize = lines.size    //add lines.size() to account for newline characters at end of each line
        for (s in lines) maxSize += s.length
        val characters = CharArray(maxSize)
        var currIdx = 0
        for (s in lines) {
            val thisLine = s.toCharArray()
            for (aThisLine in thisLine) {
                if (!this.characterMap.contains(aThisLine)) continue
                characters[currIdx++] = aThisLine
            }
            if (newLineValid) {
                characters[currIdx++] = '\n'
            }
        }

        if (currIdx == characters.size) {
            fileCharacters = characters
        } else {
            fileCharacters = Arrays.copyOfRange(characters, 0, currIdx)
        }
        if (exampleLength >= fileCharacters!!.size) {
            throw IllegalArgumentException("exampleLength=" + exampleLength
                    + " cannot exceed number of valid characters in file (" + fileCharacters!!.size + ")")
        }

        val nRemoved = maxSize - fileCharacters!!.size
        println("Loaded and converted file: " + fileCharacters!!.size + " valid characters of "
                + maxSize + " total characters (" + nRemoved + " removed)")
    }

    fun convertIndexToCharacter(idx: Int): Char {
        return this.characterMap.charAt(idx)
    }

    fun convertCharacterToIndex(c: Char): Int {
        return this.characterMap.indexOf(c)!!
    }

    val randomCharacter: Char
        get() = characterMap.sampleChar()

    override fun hasNext(): Boolean {
        return examplesSoFar + miniBatchSize <= numExamplesToFetch
    }

    override fun next(): DataSet {
        return next(miniBatchSize)
    }

    override fun next(num: Int): DataSet {
        if (examplesSoFar + num > numExamplesToFetch) throw NoSuchElementException()
        //Allocate space:
        val input = Nd4j.zeros(num, characterMap.size(), exampleLength)
        val labels = Nd4j.zeros(num, characterMap.size(), exampleLength)

        val maxStartIdx = fileCharacters!!.size - exampleLength

        //Randomly select a subset of the file. No attempt is made to avoid overlapping subsets
        // of the file in the same minibatch
        for (i in 0..num - 1) {
            var startIdx = (rng.nextDouble() * maxStartIdx).toInt()
            var endIdx = startIdx + exampleLength
            var scanLength = 0
            if (alwaysStartAtNewLine) {
                while (startIdx >= 1 && fileCharacters!![startIdx - 1] != '\n' && scanLength++ < MAX_SCAN_LENGTH) {
                    startIdx--
                    endIdx--
                }
            }

            var currCharIdx = characterMap.indexOf(fileCharacters!![startIdx])!!    //Current input
            var c = 0
            var j = startIdx + 1
            while (j <= endIdx) {
                val nextCharIdx = characterMap.indexOf(fileCharacters!![j])!!        //Next character to predict
                input.putScalar(intArrayOf(i, currCharIdx!!, c), 1.0)
                labels.putScalar(intArrayOf(i, nextCharIdx, c), 1.0)
                currCharIdx = nextCharIdx
                j++
                c++
            }
        }

        examplesSoFar += num
        return DataSet(input, labels)
    }

    override fun totalExamples(): Int {
        return numExamplesToFetch
    }

    override fun inputColumns(): Int {
        return characterMap.size()
    }

    override fun totalOutcomes(): Int {
        return characterMap.size()
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

    override fun setPreProcessor(preProcessor: DataSetPreProcessor) {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun remove() {
        throw UnsupportedOperationException()
    }
}