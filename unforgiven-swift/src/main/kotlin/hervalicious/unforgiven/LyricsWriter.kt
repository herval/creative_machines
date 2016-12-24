package hervalicious.unforgiven

import hervalicious.ai.rnn.CharacterMap
import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.FileLoader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.tumblr.PostMaker
import org.nd4j.linalg.factory.Nd4j
import java.util.*

/**
 * Created by herval on 10/31/15.
 */
class LyricsWriter(private val titleMaker: TitleMaker, private val inspiredBrain: Extractor) : PostMaker {
    private val rnd = Random()

    override fun content(): String {
        val phrases = inspiredBrain.sample(100 + rnd.nextInt(200), 1 + rnd.nextInt(4))

        // pick up 1 - 8 verses
        val verses = rnd.nextInt(8)
        val finalLyrics = ArrayList<String>()
        var pickedVerses = 0
        for (phrase in phrases) {
            finalLyrics.add(phrase)
            if (phrase.isEmpty()) {
                pickedVerses++
                if (pickedVerses == verses) {
                    break
                }
            }
        }

        return finalLyrics.joinToString("\n").trim { it <= ' ' }
    }

    override fun title(): String {
        var title = titleMaker.take(1 + rnd.nextInt(6))
        if (title.contains("(") && !title.contains(")")) {
            title += ")"
        }
        return title
    }

    companion object {

        fun build(config: Config): LyricsWriter {
            val manager = NetworkManager.load(config.networkFile, config.defaultCharacterMap)

            return LyricsWriter(
                    TitleMaker(FileLoader(config.titleFiles, CharacterMap.defaultCharacterMap)),
                    Extractor(manager)
            )
        }
    }

}
