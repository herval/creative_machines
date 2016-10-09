import hervalicious.unforgiven.Config
import hervalicious.unforgiven.LyricsWriter
import junit.framework.TestCase

/**
 * Created by herval on 9/28/16.
 */
class LyricsWriterTest : TestCase() {

    fun testTitles() {
        val maker = LyricsWriter.build(Config)

        (0..10).forEach {
            println(
                    maker.title()
            )
        }
    }

    fun testGenerator() {
        val maker = LyricsWriter.build(Config)

        println(
                maker.content()
        )
    }
}