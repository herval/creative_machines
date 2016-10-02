package hervalicious.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Load configs from either a config file (if present) or from system variables, giving preference to the env var
 *
 * Created by herval on 9/25/16.
 */
class Config(envFile: File? = File(".env")) {

    fun resource(name: String): Path {
        return Paths.get(javaClass.getResource(name).toURI())
    }

    val env: Map<String, String> by lazy {
        if (envFile != null) {
            val prop = Properties()
            try {
                FileInputStream(envFile).use {
                    prop.load(it)
                }
            } catch (e: FileNotFoundException) {
                println("*** Env file not found: ${envFile.absolutePath} ***")
            }

            prop.entries.map { e -> e.key.toString() to e.value.toString() }.toMap()
        } else {
            emptyMap<String, String>()
        }
    }

    fun get(name: String, default: String? = null): String {
        return System.getenv(name) ?: env.get(name) ?: default ?: throw IllegalArgumentException("Missing config: ${name}")
    }

}