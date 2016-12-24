package hervalicious.deepherval

import hervalicious.ai.rnn.*
import org.apache.commons.csv.CSVFormat
import java.io.File
import java.io.FileReader

/**
 * Created by hfreire on 12/24/16.
 */
class CsvLoader(input: File, val characterMap: CharacterMap) : Loader {

    private val reader = FileReader(input)
    private val records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader)

    val contents = records.records.map { it.get("text") }

    override fun iterator(trainingSet: TrainingSet): CharacterIterator {
        return CharacterIterator(
                contents,
                trainingSet.batchSize,
                trainingSet.exampleLength,
                trainingSet.examplesPerIteration,
                characterMap,
                true
        )
    }
}