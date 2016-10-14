package hervalicious.ai.rnn

import org.apache.commons.io.FileUtils
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.factory.Nd4j
import java.io.*
import java.nio.file.Path

/**
 * Created by herval on 10/30/15.
 */
class NetworkManager(
        private val coefficients: File,
        private val topology: File,
        val network: Network
) {

    fun characterMap(): CharacterMap {
        return network.characterMap
    }

    fun save() {
        val dos = DataOutputStream(FileOutputStream(coefficients))
        Nd4j.write(network.model.params(), dos)
        dos.flush()
        dos.close()

        FileUtils.writeStringToFile(topology, network.model.layerWiseConfigurations.toJson())
    }


    companion object {
        private val COEFICIENTS_FILE = "coefficients_network.bin"
        private val NETWORK_CONFIG_FILE = "conf_network.json"

        fun coefficientsFile(storagePath: Path): File {
            return File(storagePath.toFile(), COEFICIENTS_FILE)
        }

        fun topologyFile(storagePath: Path): File {
            return File(storagePath.toFile(), NETWORK_CONFIG_FILE)
        }

        fun load(storagePath: Path, characterMap: CharacterMap): NetworkManager {
            val confFromJson = MultiLayerConfiguration.fromJson(FileUtils.readFileToString(topologyFile(storagePath)))
            val dis = DataInputStream(FileInputStream(coefficientsFile(storagePath)))
            val newParams = Nd4j.read(dis)
            dis.close()

            val model = MultiLayerNetwork(confFromJson)
            model.init()
            model.setParameters(newParams)

            val network = Network(model, characterMap)

            println("Loaded network from file")

            return NetworkManager(
                    coefficientsFile(storagePath),
                    topologyFile(storagePath),
                    network
            )
        }

        fun loadOrDefault(storagePath: Path, defaultNetwork: Network): NetworkManager {
            return try {
                load(storagePath, defaultNetwork.characterMap)
            } catch (e: Exception) {
                println("Couldn't load: ${e.message}")
                defaultConfig(storagePath, defaultNetwork)
            }
        }

        fun defaultConfig(storagePath: Path, defaultNetwork: Network): NetworkManager {
            val dir = storagePath.toFile()
            dir.mkdirs()

            return NetworkManager(
                    coefficientsFile(storagePath),
                    topologyFile(storagePath),
                    defaultNetwork
            )
        }
    }
}
