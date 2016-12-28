package hervalicious.deepherval

import hervalicious.ai.rnn.*
import org.apache.commons.csv.CSVFormat
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import java.io.File
import java.io.FileReader

/**
 * Created by hfreire on 12/24/16.
 */
class CsvLoader(input: File) : Loader {

    private val reader = FileReader(input)
    private val records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader)

    val contents = records.records.map { it.get("text") }

    override fun iterator(trainingSet: TrainingSet): DataSetIterator {
        return LineIterator(
                contents,
                trainingSet.batchSize,
                trainingSet.exampleLength,
                trainingSet.examplesPerIteration,
                140 // TODO const
        )
    }
}