package hervalicious.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Load configs from either a config file (if present) or from system variables, giving precedence to the env var
 *
 * Created by herval on 9/25/16.
 */
class Env(envFile: File? = File(".env")) {

    val dataPath: Path? by lazy {
        val workDir = getOptional("WORK_DIR")
        if (workDir != null) {
            val path = Paths.get(workDir)
            println("*** WORK_DIR set to ${path.toAbsolutePath().toString()} ***")
            path
        } else {
            println("*** No WORK_DIR configured - looking up data on resources ***")
            null
        }
    }

    // load a resource from WORK_DIR (or the resources folder, by default)
    fun resource(name: String): Path {
        return dataPath?.resolve(name) ?: Paths.get(javaClass.getResource("/${name}").toURI())
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
        return getOptional(name) ?: default ?: throw IllegalArgumentException("Missing config: ${name}")
    }

    fun getOptional(name: String): String? {
        return System.getenv(name) ?: env.get(name)
    }

}