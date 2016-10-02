package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.TweetMaker

/**
 * Created by herval on 9/27/16.
 */
class ArticleWriter(val extractor: Extractor) : TweetMaker {

    override fun sample(): String {
        return extractor.sample(140, 1).first()
    }

    companion object {
        fun build(c: Config): ArticleWriter {
            val network = NetworkManager.load(
                    c.networkPath,
                    c.defaultCharacterMap
            )

            return ArticleWriter(Extractor(network))
        }

    }

}