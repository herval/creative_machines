package hervalicious.unforgiven

import hervalicious.ai.rnn.FileLoader
import hervalicious.markov.MarkovChain
import java.util.*

/**
 * Created by herval on 3/30/16.
 */
class TitleMaker(contentLoader: FileLoader) {
    private val chainsOfCreativity = MarkovChain(
            contentLoader.contents.joinToString("\n"),
            stopWords = setOf("\n")
    )

    fun take(q: Int): String {
        return chainsOfCreativity.takeWords(q, attempts = 10).joinToString(" ")
    }
}
