package hervalicious.haikuzao

import hervalicious.ai.rnn.Extractor
import hervalicious.twitter.TweetMaker

class RandomHaikuMaker(val extractor: Extractor, val dictionary: Set<String>) : TweetMaker {
    private val sequenceSize = 800
    private val maxTries = 8

    // sample a Haiku from the network
    override fun sample(): String {
        return extractor.sample(sequenceSize, maxTries)
                .flatMap { raw ->
                    splitInHaikus(raw)
                            .filter { l -> l.count { it == '\n' } == 3 } // only 3-lines...
                            .map { words ->
                                countWeirdWords(words) to words
                            }
                            .sortedBy { c -> c.first } // as few weird words as possible
                            .map { it.second }
                }
                .first()
    }

    private fun splitInHaikus(raw: String): List<String> {
        return raw.split("\n").fold(mutableListOf<MutableList<String>>(), { lists, line ->
            if (line.isEmpty()) {
                lists.add(mutableListOf("")) // a new haiku appears!
            } else {
                lists.last().add(line + "\n") // append line to the last haiku
            }
            lists
        }).map { l -> l.joinToString("\n") }.toList()
    }

    private fun countWeirdWords(words: String): Int {
        return words.replace("\n", " ")
                .split(" ")
                .filterNot { it.isEmpty() }
                .count { w -> !dictionary.contains(w.toLowerCase()) }
    }
}
