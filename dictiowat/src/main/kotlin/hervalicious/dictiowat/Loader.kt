package hervalicious.dictiowat

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

/**
 * Created by herval on 10/9/16.
 */
class Loader(jsonFile: File) {

    val words: List<String>
    val definitions: List<String>

    init {
        val mapper = jacksonObjectMapper()

        val data = mapper.readValue<Map<String, String>>(jsonFile.inputStream())
        words = data.keys.toList()
        definitions = data.values.toList()
    }
}