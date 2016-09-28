package hervalicious.util

import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Load configs from either a config file (if present) or from system variables, giving preference to the env var
 *
 * Created by herval on 9/25/16.
 */
class Config(envFile: File? = File(".env")) {

    val env: Map<String, String> by lazy {
        if (envFile != null) {
            val prop = Properties()
            FileInputStream(envFile).use {
                prop.load(it)
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