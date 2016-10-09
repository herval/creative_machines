package hervalicious.markov

import java.util.*

/**
 * A markov chain of sorts
 *
 * Created by herval on 3/30/16.
 */
class MarkovChain(
        sequences: String,
        scrubbedChars: List<String> = listOf(),
        private val stopWords: Set<String> = setOf("!", "?", "."),
        private val nonStop: Set<String> = setOf("Mr.", "Mrs.", "St.", "Ms.")
) {
    private val rnd = Random()
    private val chains = HashMap<String, List<String>>()

//    private var words

    val openers: Set<String>

    init {
        var words = sequences
        scrubbedChars.forEach { c -> words = words.replace(c, "") }

        val phrases = words.split(" ").fold(
                mutableListOf(mutableListOf<String>()),
                { phrases, w ->
                    if (w.trim().isNotBlank()) {
                        val phrase = phrases.last()
                        phrase.add(w.trim())

                        if (isStop(w)) {
                            phrases.add(mutableListOf<String>())
                        }
                    }
                    phrases
                }
        ).filter { p -> p.isNotEmpty() }

        openers = phrases.map { s -> s[0] }.toSet()

        phrases.forEach { line ->
            load(line[0], line.drop(1))
        }
    }

    private fun load(head: String, tail: List<String>) {
        if (tail.size < 1) {
            return
        }

        val next = tail[0]
        chains.merge(
                head,
                listOf(next),
                { old, new -> old.plus(new) }
        )

        load(next, tail.drop(1))
    }

    private fun random(list: Collection<String>): String? {
        if (list.isEmpty()) {
            return null
        }

        if (list.size == 1) {
            return list.elementAt(0)
        }
        return list.elementAt(rnd.nextInt(list.size - 1))
    }

    private fun isStop(word: String): Boolean {
        return !nonStop.contains(word) && stopWords.contains(word.last().toString()) // TODO regex
    }

    // take full sentences
    fun takePhrase(maxCharacters: Int, attempts: Int, opener: String? = null): String {
        val sequences = (0..attempts).flatMap {
            val seqs = takeWords(maxCharacters, opener = opener) // lots of words...
            seqs.fold(mutableListOf<StringBuilder>(StringBuilder()), { phrases, word ->
                // current chain is too long, add a new one
                if (phrases.last().length >= maxCharacters || (phrases.last().length + word.length) > maxCharacters) {
                    phrases.add(StringBuilder())
                }

//                if (isStop(word)) {
//                    phrases.last().append(word)
//                    phrases.add(StringBuilder())
//                } else {
                phrases.last().append(word + " ")
//                }

                phrases
            })
        }.map { s -> s.trim().split(" ").dropLastWhile { w -> !isStop(w) }.joinToString(" ") } // keep only full phrases

        return sequences.filter { s -> s.isNotBlank() && s.length <= maxCharacters }.
                sortedByDescending { s -> s.length }.
                first().toString()
    }

    private val maxTakeAttempts = 3

    // take n chained words
    fun takeWords(maxWords: Int, attempts: Int = maxTakeAttempts, opener: String? = null): Array<String> {
        val head = opener ?: random(openers)

        val words = ArrayList<String>()

        var prev: String = head!!
        for (i in 0..maxWords - 1) {
            words.add(prev)
            val next = chains.getOrDefault(prev, mutableListOf<String>())
            var word = random(next)
            var attempt = 0

            while (word == null && ++attempt <= attempts && next.size > 0) {
                word = random(next)
            }

            if (word != null) {
                prev = word
            } else {
                break
            }
        }

        return words.toTypedArray()
    }
}
