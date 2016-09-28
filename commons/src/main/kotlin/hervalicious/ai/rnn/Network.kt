package hervalicious.ai.rnn

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.optimize.listeners.ScoreIterationListener

/**
 * Wrapper around deeplearning4j multi-layer network
 *
 * Created by herval on 10/30/15.
 */
class Network(internal val model: MultiLayerNetwork, val characterMap: CharacterMap) {

    init {
        this.model.setListeners(ScoreIterationListener(1))

        //Print the  number of parameters in the network (and for each layer)
        val totalNumParams = model.layers.mapIndexed { i, layer ->
            val nParams = layer.numParams()
            println("Number of parameters in layer $i: $nParams")
            nParams
        }
        println("Total number of network parameters: " + totalNumParams)
    }

}
