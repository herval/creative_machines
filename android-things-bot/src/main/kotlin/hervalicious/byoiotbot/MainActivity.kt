package hervalicious.byoiotbot

import android.app.Activity
import android.os.Bundle
import hervalicious.markovian.Config
import hervalicious.markovian.text.Writer
import hervalicious.twitter.Bot

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        val bot = Bot(
                Config,
                Writer()
        )

        val proc = Thread(bot)
        proc.run()
        proc.join()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
