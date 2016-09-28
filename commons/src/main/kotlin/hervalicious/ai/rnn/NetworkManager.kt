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
        defaultNetwork: Network
) {
    var network = defaultNetwork

    fun characterMap(): CharacterMap {
        return network.characterMap
    }

    fun load(): NetworkManager {
        val confFromJson = MultiLayerConfiguration.fromJson(FileUtils.readFileToString(topology))
        val dis = DataInputStream(FileInputStream(coefficients))
        val newParams = Nd4j.read(dis)
        dis.close()

        val model = MultiLayerNetwork(confFromJson)
        model.init()
        model.setParameters(newParams)

        network = Network(model, network.characterMap)
        return this
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

        fun defaultConfig(storagePath: Path, network: Network): NetworkManager {
            val dir = storagePath.toFile()
            dir.mkdirs()

            return NetworkManager(
                    File(storagePath.toFile(), COEFICIENTS_FILE),
                    File(storagePath.toFile(), NETWORK_CONFIG_FILE),
                    network
            )
        }
    }
}
