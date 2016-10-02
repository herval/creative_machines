package hervalicious.unforgiven

/**
 * Created by herval on 10/31/15.
 */
data class Song(val title: String, val lyrics: String) {

    override fun toString(): String {
        return title + "\n\n" + lyrics
    }
}
