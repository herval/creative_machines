import hervalicious.markovian.text.BookCollection
import hervalicious.markovian.text.BookPair
import junit.framework.TestCase

/**
 * Created by herval on 10/3/16.
 */
class BookPairTest : TestCase() {

    fun testPair() {
        val pair = BookPair(
                BookCollection.all[0],
                BookCollection.all[1]
        )

        val quote = pair.quote()
        println(quote)
    }

}
