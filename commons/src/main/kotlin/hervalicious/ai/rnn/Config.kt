package hervalicious.ai.rnn

import java.io.File

/**
 * Created by herval on 9/27/16.
 */
interface Config {

    // the .zip file w/ network training
    val networkFile: File

    // blank topology (for a new network)
    val defaultTopology: Network

}