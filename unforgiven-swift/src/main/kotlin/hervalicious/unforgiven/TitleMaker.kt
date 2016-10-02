package hervalicious.unforgiven

import hervalicious.ai.rnn.Loader
import hervalicious.markov.MarkovChain
import java.util.*

/**
 * Created by herval on 3/30/16.
 */
class TitleMaker(contentLoader: Loader) {
    private val chainsOfCreativity = MarkovChain(contentLoader.contents)

    fun take(q: Int): String {
        return chainsOfCreativity.take(q).joinToString(" ")
    }
}
