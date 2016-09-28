package hervalicious.dictiowat

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
object Config : hervalicious.twitter.Config {
    private val conf = hervalicious.util.Config()

    override val accessToken: String by lazy { conf.get("TWITTER_ACCESS_TOKEN") }
    override val accessTokenSecret: String by lazy { conf.get("TWITTER_ACCESS_TOKEN_SECRET") }
    override val consumerSecret: String by lazy { conf.get("TWITTER_CONSUMER_SECRET") }
    override val consumerToken: String by lazy { conf.get("TWITTER_CONSUMER_TOKEN") }
    override val sleepInterval = 60 * 60 * 1000L

    val rawContent = Paths.get("dictiowat/src/main/resources/dictionary.txt")

    val networkPath = Paths.get("dictiowat/src/main/resources/networks/200_neurons")

    val defaultCharacterMap = CharacterMap.defaultCharacterMap

    fun defaultTopology(characterMap: CharacterMap = defaultCharacterMap): Network {
        val LAYER_SIZE = 200 //Number of units in each GravesLSTM layer

        val config = NeuralNetConfiguration.Builder().
                optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).
                iterations(1).
                learningRate(0.01).
                rmsDecay(0.97).
                seed(12345).
                regularization(true).
                l1(0.001).
                list(3).
                layer(0, GravesLSTM.Builder().
                        nIn(characterMap.size()).
                        nOut(LAYER_SIZE).
                        updater(Updater.RMSPROP).
                        activation("tanh").
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                layer(1, GravesLSTM.Builder().
                        nIn(LAYER_SIZE).
                        nOut(LAYER_SIZE).
                        updater(Updater.RMSPROP).
                        activation("tanh").
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                layer(2, RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).
                        activation("softmax").//MCXENT + softmax for classification
                        updater(Updater.RMSPROP).
                        nIn(LAYER_SIZE).
                        nOut(characterMap.size()).
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                pretrain(false).
                backprop(true).
                build()

        val model = MultiLayerNetwork(config)
        model.init()

        return Network(model, characterMap)
    }
}
