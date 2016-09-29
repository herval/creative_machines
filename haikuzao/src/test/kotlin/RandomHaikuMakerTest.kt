import hervalicious.haikuzao.Config
import hervalicious.haikuzao.RandomHaikuMaker
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class RandomHaikuMakerTest : TestCase() {

    fun testGenerator() {
        val maker = RandomHaikuMaker.build(Config)

        println(
                maker.sample()
        )
    }
}