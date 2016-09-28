package hervalicious.ai.rnn

import java.nio.file.Path

/**
 * Created by herval on 9/27/16.
 */
interface Config {

    val networkPath: Path
    val defaultTopology: Network

}