package hervalicious.util

import java.util.logging.Logger

/**
 * Created by hfreire on 12/27/16.
 */
interface Logging {

    val logger: Logger// = Logger.getLogger("out")

    fun println(str: String) {
        logger.info(str)
//        System.out.println(str)
    }


}