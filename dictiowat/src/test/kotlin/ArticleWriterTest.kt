import hervalicious.dictiowat.ArticleWriter
import hervalicious.dictiowat.Config
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class ArticleWriterTest : TestCase() {

    fun testDefinitions() {
        val maker = ArticleWriter.build(Config())

        0.until(10).forEach {
            println(maker.definition(100))
        }
    }

    fun testGenerator() {
        val maker = ArticleWriter.build(Config())

        0.until(10).forEach {
            println(maker.sample())
        }
    }
}