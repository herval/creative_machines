package hervalicious.ai.rnn

interface Extractor {
    fun sample(characters: Int, numSamples: Int): Array<String>
}
