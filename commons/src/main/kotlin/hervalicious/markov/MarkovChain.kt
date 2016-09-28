package hervalicious.markov

import java.util.*

/**
 * A markov chain of sorts
 *
 * Created by herval on 3/30/16.
 */
class MarkovChain(sequences: List<String>) {
    private val rnd = Random()
    private val chains = HashMap<String, List<String>>()

    private val words = sequences.map { s -> s.split(" ") }

    private val openers = words.map { s -> s[0] }.toSet()

    init {
        words.forEach { line ->
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

    private val MAX_TAKE_ATTEMPTS = 3

    fun take(maxWords: Int): Array<String> {
        val head = random(openers)

        val words = ArrayList<String>()

        var prev: String = head!!
        for (i in 0..maxWords - 1) {
            words.add(prev)
            val next = chains.getOrDefault(prev, mutableListOf<String>())
            var word = random(next)
            var attempt = 0

            while (word == null && ++attempt <= MAX_TAKE_ATTEMPTS && next.size > 0) {
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
