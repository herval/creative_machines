package hervalicious.ai.rnn

import org.nd4j.linalg.factory.Nd4j
import java.util.*

/**
 * Extract samples from a network w/o a character map and a fixed input size
 */
class FixedWidthUnicodeExtractor(network: NetworkManager, private val inputSize: Int) : Extractor {
    private val network: Network
    private val rng = Random()

    init {
        this.network = network.network
    }

    override fun sample(characters: Int, numSamples: Int): Array<String> {
        return samplesFromNetwork(null, numSamples)
    }

    /**
     * Generate a sample from the network, given an (optional, possibly null) initialization. Initialization
     * can be used to 'prime' the RNN with a sequence you want to extend/continue.
     * Note that the initalization is used for all samples
     */
    private fun samplesFromNetwork(startOfSequence: String?, numSamples: Int): Array<String> {

        //Set up initialization. If no initialization: use a random character
        val initialization = startOfSequence ?: CharacterMap.letterOnlyCharacterSet.sampleChar().toString()

        //Create input for initialization
        val initializationInput = Nd4j.zeros(numSamples, inputSize)
        for (i in initialization.indices) {
//            val idx = initialization[i].toInt()
            for (j in 0..(numSamples - 1)) {
                initializationInput.put(j, i, initialization.get(i).toInt())
            }
        }

        val sampleBuilders = (0..(numSamples - 1)).map {
            StringBuilder(initialization)
        }

        val distribution = NumericDistribution()

        //Sample from network (and feed samples back into input) one character at a time (for all samples)
        //Sampling is done in parallel here
        network.model.rnnClearPreviousState()
        val output = network.model.rnnTimeStep(initializationInput)
//        output = output.tensorAlongDimension(1, 0)    //Gets the last time step output

        for (i in 0..(inputSize - 1)) {
            //Set up next input (single time step) by sampling from previous output
            val nextInput = Nd4j.zeros(numSamples, inputSize)
            //Output is a probability distribution. Sample from this for each example we want to generate, and add it to the new input
            for (sampleNumber in 0..(numSamples - 1)) {
                val outputProbDistribution = (0..(inputSize - 1)).map { charIndex ->
                    // TODO this sometimes returns NaN. WHY!?!?
                    output.getDouble(sampleNumber, charIndex)
                }

                val sampledCharacter = distribution.sample(outputProbDistribution)
                if(sampledCharacter != null) {
                    nextInput.put(sampleNumber, i, sampledCharacter)        //Prepare next time step input
                    sampleBuilders[sampleNumber].append(sampledCharacter.toChar())    //Add sampled character to StringBuilder (human readable output)
                }
            }

            output = network.model.rnnTimeStep(nextInput)    //Do one time step of forward pass
        }

        return sampleBuilders.map { l -> l.toString() }.toTypedArray()
    }

}
