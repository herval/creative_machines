import hervalicious.dictiowat.ArticleWriter
import hervalicious.dictiowat.Config
import hervalicious.dictiowat.Loader
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class LoaderTest : TestCase() {

    fun testLoading() {
        val loader = Loader(Config().jsonContent)

        assert(loader.words.isNotEmpty())
        assert(loader.definitions.size == loader.words.size)
    }
}