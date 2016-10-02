import hervalicious.ai.rnn.CharacterMap
import hervalicious.markov.MarkovChain
import junit.framework.TestCase

/**
 * Created by herval on 3/30/16.
 */
class CharacterMapTest : TestCase() {

    fun testDefault() {
        val map = CharacterMap.defaultCharacterMap
        assert(map.size() == 84)
    }

}
