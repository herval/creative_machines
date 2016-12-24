package hervalicious.dictiowat

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

/**
 * Created by herval on 10/31/15.
 */
class Config : hervalicious.twitter.Config() {

    val jsonContent = env.resource("dictionary.json").toFile()

    val definitionsNetworkPath = env.resource("definitions.zip").toFile()

    val definitionsCharacterMap = CharacterMap.defaultCharacterMap

    fun definitionsTopology(characterMap: CharacterMap = definitionsCharacterMap): Network {
        val layerSize = 350 //Number of units in each GravesLSTM layer

        val config = NeuralNetConfiguration.Builder().
                optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).
                iterations(1).
                learningRate(0.002).
                rmsDecay(0.97).
                seed(12345).
                regularization(true).
                l1(0.001).
                list().
                layer(0, GravesLSTM.Builder().
                        nIn(characterMap.size()).
                        nOut(layerSize).
                        updater(Updater.RMSPROP).
                        activation("tanh").
                        weightInit(WeightInit.DISTRIBUTION).
                        dist(UniformDistribution(-0.08, 0.08)).
                        build()).
                layer(1, RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).
                        activation("softmax").//MCXENT + softmax for classification
                        updater(Updater.RMSPROP).
                        nIn(layerSize).
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

    val wordsNetworkPath = env.resource("words.zip").toFile()

    val wordsCharacterMap = CharacterMap.lettersAndSpacesOnlyCharacterSet

    fun wordsTopology(characterMap: CharacterMap = wordsCharacterMap): Network {
        val layerSize = 100 //Number of units in each GravesLSTM layer

        val config = NeuralNetConfiguration.Builder().
                optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).
                iterations(1).
                learningRate(0.002).
                rmsDecay(0.97).
                seed(12345).
                regularization(true).
                l1(0.001).
                list().
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
                        activation("softmax").//MCXENT + softmax for classification
                        updater(Updater.RMSPROP).
                        nIn(layerSize).
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
