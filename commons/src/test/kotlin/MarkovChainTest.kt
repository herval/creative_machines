import hervalicious.markov.MarkovChain
import junit.framework.TestCase

/**
 * Created by herval on 3/30/16.
 */
class MarkovChainTest : TestCase() {

    internal var chain = MarkovChain(listOf("Hello world again"))

    fun testLoad() {
        assertEquals("Hello world again", chain.take(3).joinToString(" "))
    }

}
