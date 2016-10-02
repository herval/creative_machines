import hervalicious.ai.rnn.CharacterMap
import junit.framework.TestCase

/**
 * Created by herval on 3/30/16.
 */
class CharacterMapTest : TestCase() {

    fun testSizes() {
        assert(CharacterMap.defaultCharacterMap.size() == 94)
        assert(CharacterMap.minimalCharacterMap.size() == 77)

    }

}
