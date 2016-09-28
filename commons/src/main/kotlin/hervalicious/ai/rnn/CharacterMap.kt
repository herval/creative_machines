package hervalicious.ai.rnn

import java.util.*

/**
 * Store valid characters is a map for later use in vectorization
 *
 * Created by herval on 10/31/15.
 */
class CharacterMap(private val validCharacters: List<Char>) {
    private val rng = Random()
    private val charToIdxMap = validCharacters.mapIndexed { i, c -> (c to i) }.toMap()

    operator fun contains(c: Char): Boolean {
        return charToIdxMap.containsKey(c)
    }

    fun charAt(idx: Int): Char {
        return this.validCharacters[idx]
    }

    fun indexOf(c: Char): Int? {
        return charToIdxMap[c]
    }

    fun sampleChar(): Char {
        return validCharacters[(rng.nextDouble() * validCharacters.size).toInt()]
    }

    fun size(): Int {
        return validCharacters.size
    }

    companion object {
        private val minimalCharacterSet = ('a'..'z').toList() +
                ('A'..'Z').toList() +
                listOf('!', '&', '(', ')', '?', '-', '\'', '"', ',', '.', ':', ';', ' ', '\n', '\t')

        /**
         * A minimal character set, with a-z, A-Z, 0-9 and common punctuation etc
         */
        val minimalCharacterMap = CharacterMap(minimalCharacterSet)

        /**
         * As per getMinimalCharacterSet(), but with a few extra characters
         */
        val defaultCharacterMap = CharacterMap(
                minimalCharacterSet +
                        listOf('@', '#', '$', '%', '^', '*', '{', '}', '[', ']', '/', '+', '_', '\\', '|', '<', '>')
        )

    }
}
