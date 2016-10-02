package hervalicious.ai.rnn

import java.util.Random

/**
 * Created by herval on 10/30/15.
 */
class NumericDistribution {
    private val random = Random()

    /**
     * Given a probability distribution over discrete classes, sample from the distribution
     * and return the generated class index.

     * @param distribution Probability distribution over classes. Must sum to 1.0
     */
    fun sample(distribution: List<Double>): Int? {
        val d = random.nextDouble()
        var sum: Double = 0.0
        for (i in distribution.indices) {
            sum += distribution[i]
            if (d <= sum) {
                return i
            }
        }

        return null
        //Should never happen if distribution is a valid probability distribution - BUT IT DOES
//        throw IllegalArgumentException("Distribution is invalid? d=$d, sum=$sum l=${distribution}")
    }
}
