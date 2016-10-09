package hervalicious.haikuzao

import hervalicious.ai.rnn.Extractor
import hervalicious.ai.rnn.FileLoader
import hervalicious.ai.rnn.NetworkManager
import hervalicious.twitter.TweetMaker

class RandomHaikuMaker(private val extractor: Extractor, private val dictionary: Set<String>) : TweetMaker {
    private val sequenceSize = 800
    private val maxTries = 8

    // sample a Haiku from the network
    override fun sample(): String {
        return extractor.sample(sequenceSize, maxTries).
                flatMap { raw ->
                    splitInHaikus(raw).
                            filter { l -> l.count { it == '\n' } == 2 }. // only 3-lines...
                            map { words ->
                                countWeirdWords(words) to words
                            }.
                            sortedBy { it.first }. // as few weird words as possible
                            map { it.second }
                }.first()
    }

    fun splitInHaikus(raw: String): List<String> {
        val results = mutableListOf<MutableList<String>>(mutableListOf())

        raw.split("\n").map { line ->
            if(line.isBlank()) {
                results.add(mutableListOf())
            } else {
                results.last().add(line)
            }
        }

        return results.map { r ->
            r.joinToString("\n")
        }.filter { h ->
            h.isNotBlank()
        }
    }

    private fun countWeirdWords(words: String): Int {
        return words.replace("\n", " ").
                split(" ").
                filterNot { it.isEmpty() }.
                count { w -> !dictionary.contains(w.toLowerCase()) }
    }


    companion object {

        fun build(c: Config): RandomHaikuMaker {
            val network = NetworkManager.load(
                    c.networkPath,
                    c.defaultCharacterMap
            )

            val data = FileLoader(listOf(Config.rawContent), network.characterMap()).contents
            val dictionary = data.map { l -> l.split(" ") }.flatten().map { w -> w.toLowerCase() }.toSet()

            return RandomHaikuMaker(Extractor(network), dictionary)
        }

    }
}
