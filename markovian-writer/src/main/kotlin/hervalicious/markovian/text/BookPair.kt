package hervalicious.markovian.text

import hervalicious.markov.MarkovChain

/**
 * Created by herval on 9/27/16.
 */
class BookPair(
        first: BookTitle,
        second: BookTitle,
        maxSentences: Int,
        attempts: Int = 15
) {

    fun quote(): String {
        val chars = 140 - title.length - tags.length
        return chain.take(
                maxChars = chars,
                maxSentences = maxSentences,
                attempts = attempts
        ).map {
            words => "${words}\n\n- ${title}\n\n${tags}"
        }
    }

    private val chain = MarkovChain(
            (GutenbergEbookLoader(first.filename).text + "\n" + GutenbergEbookLoader(second.filename).text).split(" ")
    )

    private val title = first.prefix.replace("$", second.postfix)
    private val tags = "${first.hashtags} ${second.hashtags}"
}