package hervalicious.haikuzao

import hervalicious.ai.rnn.CharacterMap
import hervalicious.ai.rnn.Network
import hervalicious.util.Config
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
import java.nio.file.Paths
import java.util.*

/**
 * Created by herval on 10/31/15.
 */
object Config : hervalicious.twitter.Config() {

    val rawContent = conf.resource("/haiku.txt").toFile()

    val networkPath = conf.resource("/networks/200_neurons")

    val layerSize = 200

    val defaultCharacterMap = CharacterMap.minimalCharacterMap

    fun defaultTopology(characterMap: CharacterMap = defaultCharacterMap): Network {
        val config = NeuralNetConfiguration.Builder().
                optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).
                iterations(1).
                learningRate(0.002).
                rmsDecay(0.97).
                regularization(true).
                l2(0.001).
                list(3).
                layer(0, GravesLSTM.Builder().
                        nIn(characterMap.size()).
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
                        nOut(characterMap.size()).
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                pretrain(false).
                backprop(true)

        val model = MultiLayerNetwork(config.build())
        model.init()

        return Network(model, characterMap)
    }
}
