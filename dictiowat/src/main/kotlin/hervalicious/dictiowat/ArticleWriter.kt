package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.TweetMaker
import java.util.*

/**
 * Created by herval on 9/27/16.
 */
class ArticleWriter(val wordExtractor: Extractor, val definitionExtractor: Extractor) : TweetMaker {
    val rnd = Random()

    override fun sample(): String {
        val word = wordExtractor.sample(2 + rnd.nextInt(15), 1).first()
        val def = definitionExtractor.sample(140 - word.length - 3, 1).first()

        return "${word}: ${def}"
    }

    companion object {
        fun build(c: Config): ArticleWriter {
            return ArticleWriter(
                    Extractor(NetworkManager.load(
                            c.wordsNetworkPath,
                            c.wordsCharacterMap
                    )),
                    Extractor(NetworkManager.load(
                            c.definitionsNetworkPath,
                            c.definitionsCharacterMap
                    ))
            )
        }

    }

}