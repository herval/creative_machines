package hervalicious.markovian.text

import java.io.File
import java.nio.file.Paths

/**
 * Created by herval on 9/27/16.
 */
class GutenbergEbookLoader(filename: String) {

    val text = stripLicense(loadText(filename))

    // WAT.
    private fun stripLicense(text: String): String {
        val opening = """\*\*\*[^\*]+\*\*\*""".toRegex()
        val start = opening.find(text)?.range?.endInclusive ?: 0

        val closing = """\*\*\* END OF THIS PROJECT GUTENBERG""".toRegex()
        val end = closing.find(text)?.range?.start ?: text.length

        return text.drop(start).take(text.length - end - start)
    }

    private fun loadText(filename: String): String {
        return File(
                javaClass.getResource("/books/${filename}").toURI()
        ).readLines().joinToString("\n")
    }
}