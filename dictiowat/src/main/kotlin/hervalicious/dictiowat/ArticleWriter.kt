package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.NetworkManager
import hervalicious.markov.MarkovChain
import hervalicious.twitter.TweetMaker
import java.util.*

/**
 * Created by herval on 9/27/16.
 */
class ArticleWriter(val wordExtractor: Extractor, val definitionExtractor: MarkovChain) : TweetMaker {
    val rnd = Random()

    override fun sample(): String {
        val word = word(17)
        val def = definition(140 - word.length - 3)

        return "${word}: ${def}"
    }

    fun word(maxSize: Int): String {
        return wordExtractor.sample(2 + rnd.nextInt(maxSize - 2), 1).first()
    }

    fun definition(maxSize: Int): String {
        return definitionExtractor.takePhrase(maxSize, 10)
    }

    companion object {
        fun build(c: Config): ArticleWriter {
            return ArticleWriter(
                    Extractor(NetworkManager.load(
                            c.wordsNetworkPath,
                            c.wordsCharacterMap
                    )),
                    MarkovChain(
                        Loader(c.jsonContent).definitions.joinToString(" ")
                    )
            )
        }

    }

}