import hervalicious.haikuzao.Config
import hervalicious.haikuzao.RandomHaikuMaker
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class RandomHaikuMakerTest : TestCase() {

    fun testSplit() {
        val maker = RandomHaikuMaker.build(Config)
        val haikus = maker.splitInHaikus("""
new March snow
the grouse with a missing toe
the grouse with a missing toe
still around

Remembrance Day-
pauses for 2 minutes

21-gun salute-
mortar fire echoes
in his eyes
        """)

        assert(haikus.size == 3)
    }

    fun testGenerator() {
        val maker = RandomHaikuMaker.build(Config)
        println(
                maker.sample()
        )
    }
}