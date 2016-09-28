package hervalicious.dictiowat

import hervalicious.ai.rnn.Extractor
import hervalicious.twitter.TweetMaker

/**
 * Created by herval on 9/27/16.
 */
class ArticleWriter(val extractor: Extractor) : TweetMaker{

    override fun sample(): String {
        return extractor.sample(140, 1).first()
    }

}