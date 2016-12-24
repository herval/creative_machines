package hervalicious.ai.rnn

import org.deeplearning4j.util.ModelSerializer
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by herval on 10/30/15.
 */
class NetworkManager(
        private val networkFile: File,
        val network: Network
) {

    fun characterMap(): CharacterMap {
        return network.characterMap
    }

    fun save() {
        ModelSerializer.writeModel(network.model, networkFile, true)
    }

    companion object {

        fun load(networkFile: File, characterMap: CharacterMap): NetworkManager {
            val model = ModelSerializer.restoreMultiLayerNetwork(networkFile)

            val network = Network(model, characterMap)

            println("Loaded network from file")

            return NetworkManager(
                    networkFile,
                    network
            )
        }

        fun loadOrDefault(storagePath: File, defaultNetwork: Network): NetworkManager {
            return try {
                load(storagePath, defaultNetwork.characterMap)
            } catch (e: Exception) {
                println("Couldn't load: ${e.message}")
                defaultConfig(storagePath, defaultNetwork)
            }
        }

        fun defaultConfig(networkFile: File, defaultNetwork: Network): NetworkManager {
            networkFile.parentFile.mkdirs()

            return NetworkManager(
                    networkFile,
                    defaultNetwork
            )
        }
    }
}
