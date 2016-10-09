import hervalicious.markov.MarkovChain
import junit.framework.TestCase

/**
 * Created by herval on 3/30/16.
 */
class MarkovChainTest : TestCase() {

    fun testWords() {
        val chain = MarkovChain("Hello world again")
        assertEquals("Hello world again", chain.takeWords(3).joinToString(" "))
    }

    fun testOpeners() {
        val chain = MarkovChain("Hello world again. So many phrases. ZOMG!")
        assertEquals(setOf("Hello", "So", "ZOMG!"), chain.openers)
    }


    fun testPhrases() {
        val phrases = "Hello world again. So many phrases. ZOMG!"
        val chain = MarkovChain(phrases)
        assertEquals("Hello world again.", chain.takePhrase(phrases.length, 1, opener = "Hello"))
    }
}
