package hervalicious.markovian.text

import java.util.*

/**
 * Created by herval on 10/1/16.
 */
class BookCollection(private val availableBooks: List<BookTitle> = BookCollection.all) {
    val rnd = Random()
    val maxSentences = 3

    fun randomBookCombo(): BookPair {
        return BookPair(
                availableBooks.get(rnd.nextInt(availableBooks.size)),
                availableBooks.get(rnd.nextInt(availableBooks.size)),
                maxSentences
        )
    }

    companion object {
        val all = listOf(
                BookTitle("Anna Karenina in $", "Anna Karenina", "1399-0.txt", "#Tolstoy"),
                BookTitle("The King James' $", "Bible", "pg10.txt", "#Bible"),
                BookTitle("$'s Persuasion", "Persuasion", "pg105.txt", "#JaneAusten"),
                BookTitle("Alice in $", "Wonderland", "pg11.txt", "#Alice #LewisCarroll"),
                BookTitle("Pride, Prejudice and $", "Pride and Prejudice", "pg1342.txt", "#JaneAusten"),
                BookTitle("$'s Great Expectations", "Great Expectations", "pg1400.txt", "#Dickens"),
                BookTitle("Peter Pan in $", "Peter Pan", "pg16.txt", "#PeterPan"),
                BookTitle("The Adventures of $", "Sherlock Holmes", "pg1661.txt", "#Sherlock #ConanDoyle"),
                BookTitle("The Picture of $", "Dorian Gray", "pg174.txt", "#DorianGray #OscarWilde"),
                BookTitle("Authobiography of $", "Benjamin Franklin", "pg20203.txt", "#BenjaminFranklin #Biografies"),
                BookTitle("Grimm's $ Tales", "Brothers Grimm", "pg2591.txt", "#Fairies #Grimm"),
                BookTitle("War, Peace and $", "War and Peace", "pg2600.txt", "#War #Peace #Tolstoy"),
                BookTitle("The Hounds of $", "Baskerville", "pg2852.txt", "#ConanDoyle"),
                BookTitle("Leviathan's $", "Leviathan", "pg3207.txt", "#Hobbes"),
                BookTitle("The Ladies' Book of $", "Etiquette", "pg35123.txt", "#Etiquette #FlorenceHartley"),
                BookTitle("A Christmas Carol for $", "A Ghost Story", "pg46.txt", "#Dickens #Christmas"),
                BookTitle("The Adventures of Tom Sawyer and $", "Tom Sawyer", "pg74.txt", "#TomSawyer #Twain"),
                BookTitle("$'s Frankenstein", "Frankenstein", "pg84.txt", "#MaryShelley"),
                BookTitle("The Prince of $", "Prince", "pg1232.txt", "#machiavelli"),
                BookTitle("The $ Comedy", "Divine Comedy", "pg8800.txt", "#dante #alighieri"),
                BookTitle("The $'s Koran", "Koran", "pg7440.txt", "#bible #koran"),
                BookTitle("$'s Metamorphosis", "Metamorphosis", "pg5200.txt", "#kafka"),
                BookTitle("$'s Dracula", "Dracula", "pg345.txt", "#BramStoker #Dracula"),
                BookTitle("Moby Dick and $", "Moby Dick", "pg2701.txt", "#MobyDick"),
                BookTitle("Ulysses and $", "Ulysses", "pg4300.txt", "#JamesJoyce"),
                BookTitle("$'s Romance of Lust", "Romance of Lust", "pg30254.txt", "#romance")
        )

    }
}