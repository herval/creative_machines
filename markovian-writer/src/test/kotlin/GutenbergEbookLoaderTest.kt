import hervalicious.markovian.text.GutenbergEbookLoader
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class GutenbergEbookLoaderTest : TestCase() {

    fun testLoading() {
        val loader = GutenbergEbookLoader("1399-0.txt")

        assert(loader.text.indexOf("END OF THIS PROJECT GUTENBERG") == -1)
    }
}