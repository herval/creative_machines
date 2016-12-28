package hervalicious.ai.rnn

import org.nd4j.linalg.factory.Nd4j

/**
 * Extract samples from a network
 *
 * Created by herval on 10/30/15.
 */
class CharacterMapExtractor(network: NetworkManager) : Extractor {
    private val network: Network
    private val characterMap: CharacterMap

    init {
        this.network = network.network
        this.characterMap = network.characterMap()
    }

    override fun sample(characters: Int, numSamples: Int): Array<String> {
        return samplesFromNetwork(null, characters, numSamples)
    }

    /**
     * Generate a sample from the network, given an (optional, possibly null) initialization. Initialization
     * can be used to 'prime' the RNN with a sequence you want to extend/continue.
     * Note that the initalization is used for all samples
     */
    private fun samplesFromNetwork(startOfSequence: String?, charactersToSample: Int, numSamples: Int): Array<String> {
        //Set up initialization. If no initialization: use a random character
        val initialization = startOfSequence ?: characterMap.sampleChar().toString()

        //Create input for initialization
        val initializationInput = Nd4j.zeros(numSamples, characterMap.size(), initialization.length)
        for (i in initialization.indices) {
            val idx = characterMap.indexOf(initialization[i])
            for (j in 0..(numSamples - 1)) {
                initializationInput.putScalar(intArrayOf(j, idx!!, i), 1.0f)
            }
        }

        val sampleBuilders = (0..(numSamples - 1)).map {
            StringBuilder(initialization)
        }

        val distribution = NumericDistribution()

        //Sample from network (and feed samples back into input) one character at a time (for all samples)
        //Sampling is done in parallel here
        network.model.rnnClearPreviousState()
        var output = network.model.rnnTimeStep(initializationInput)
        output = output.tensorAlongDimension(output.size(2) - 1, 1, 0)    //Gets the last time step output

        for (i in 0..(charactersToSample - 1)) {
            //Set up next input (single time step) by sampling from previous output
            val nextInput = Nd4j.zeros(numSamples, characterMap.size())
            //Output is a probability distribution. Sample from this for each example we want to generate, and add it to the new input
            for (sampleNumber in 0..(numSamples - 1)) {
                val outputProbDistribution = (0..(characterMap.size() - 1)).map { charIndex ->
                    // TODO this sometimes returns NaN. WHY!?!?
                    output.getDouble(sampleNumber, charIndex)
                }

                val sampledCharacterIdx = distribution.sample(outputProbDistribution)
                if(sampledCharacterIdx != null) {
                    nextInput.putScalar(intArrayOf(sampleNumber, sampledCharacterIdx), 1.0f)        //Prepare next time step input
                    sampleBuilders[sampleNumber].append(characterMap.charAt(sampledCharacterIdx))    //Add sampled character to StringBuilder (human readable output)
                }
            }

            output = network.model.rnnTimeStep(nextInput)    //Do one time step of forward pass
        }

        return sampleBuilders.map { l -> l.toString() }.toTypedArray()
    }

}
