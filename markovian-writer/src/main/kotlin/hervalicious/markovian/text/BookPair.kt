package hervalicious.markovian.text

import hervalicious.markov.MarkovChain

/**
 * Created by herval on 9/27/16.
 */
class BookPair(
        first: BookTitle,
        second: BookTitle,
        private val attempts: Int = 15
) {

    fun quote(): String {
        val chars = 140 - title.length - tags.length
        val quote = chain.takePhrase(
                maxCharacters = chars,
                attempts = attempts
        )
        return "${quote}\n\n- ${title}\n\n${tags}"
    }

    private val chain = MarkovChain(
            (GutenbergEbookLoader(first.filename).text + " " + GutenbergEbookLoader(second.filename).text),
            scrubbedChars = listOf("(", ")")
    )

    private val title = first.prefix.replace("$", second.postfix)
    private val tags = "${first.hashtags} ${second.hashtags}"
}