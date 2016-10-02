package hervalicious.markovian.text

import hervalicious.twitter.TweetMaker

/**
 * Created by herval on 10/1/16.
 */
class Writer() : TweetMaker {

    val collection = BookCollection()

    override fun sample(): String {
        return collection.randomBookCombo().quote()
    }
}