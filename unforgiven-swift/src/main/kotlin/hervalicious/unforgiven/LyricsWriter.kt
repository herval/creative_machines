package hervalicious.unforgiven

import hervalicious.ai.rnn.CharacterMap
import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.Loader
import hervalicious.ai.rnn.NetworkManager
import org.nd4j.linalg.factory.Nd4j
import java.util.*

/**
 * Created by herval on 10/31/15.
 */
class LyricsWriter(private val titleMaker: TitleMaker, private val inspiredBrain: Extractor) {
    private val rnd = Nd4j.getRandom()

    fun writeASong(): Song {
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

        val lyrics = finalLyrics.joinToString("\n").trim { it <= ' ' }

        return Song(
                makeUpTitle(),
                lyrics
        )
    }

    fun makeUpTitle(): String {
        var title = titleMaker.take(1 + rnd.nextInt(5))
        if (title.contains("(") && !title.contains(")")) {
            title += ")"
        }
        return title
    }

    companion object {

        fun build(config: Config): LyricsWriter {
            val manager = NetworkManager.defaultConfig(config.networkPath, config.defaultTopology())
            manager.load()

            return LyricsWriter(
                    TitleMaker(Loader(config.titleFiles, CharacterMap.defaultCharacterMap)),
                    Extractor(manager)
            )
        }
    }

}
