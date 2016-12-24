package hervalicious.unforgiven

import hervalicious.ai.rnn.CharacterMap
import hervalicious.ai.rnn.Network
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.Updater
import org.deeplearning4j.nn.conf.distribution.UniformDistribution
import org.deeplearning4j.nn.conf.layers.GravesLSTM
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.lossfunctions.LossFunctions
import java.io.File

/**
 * Created by herval on 10/31/15.
 */
object Config : hervalicious.tumblr.Config(), hervalicious.ai.rnn.Config {

    val rawContent = env.resource("/lyrics.txt").toFile()

    override val networkFile = env.resource("/200_neurons.zip").toFile()

    val titleFiles = listOf(
            env.resource("/metallica_titles.txt").toFile(),
            env.resource("/taylor_swift_titles.txt").toFile()
    )

    val defaultCharacterMap = CharacterMap.minimalCharacterMap

    override val defaultTopology: Network by lazy {
        val layerSize = 200

        val config = NeuralNetConfiguration.Builder().
                optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).
                iterations(1).
                learningRate(0.002).
                rmsDecay(0.97).
                regularization(true).
                l2(0.001).
                list().
                layer(0, GravesLSTM.Builder().
                        nIn(defaultCharacterMap.size()).
                        nOut(layerSize).
                        updater(Updater.RMSPROP).
                        activation("tanh").
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                layer(1, GravesLSTM.Builder().
                        nIn(layerSize).
                        nOut(layerSize).
                        updater(Updater.RMSPROP).
                        activation("tanh").
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                layer(2, RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).
                        activation("softmax"). //MCXENT + softmax for classification
                        updater(Updater.RMSPROP).
                        nIn(layerSize).
                        nOut(defaultCharacterMap.size()).
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                pretrain(false).
                backprop(true)

        val model = MultiLayerNetwork(config.build())
        model.init()

        Network(model, defaultCharacterMap)
    }
}
