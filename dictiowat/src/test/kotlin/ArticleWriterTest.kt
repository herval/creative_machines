import hervalicious.dictiowat.ArticleWriter
import hervalicious.dictiowat.Config
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class ArticleWriterTest : TestCase() {

    fun testGenerator() {
        val maker = ArticleWriter.build(Config)

        println(
                maker.sample()
        )
    }
}