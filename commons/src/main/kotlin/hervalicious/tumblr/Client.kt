package hervalicious.tumblr

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.types.TextPost

/**
 * Created by herval on 10/31/15.
 */
class Client(consumerKey: String,
             consumerSecret: String,
             private val blogName: String,
             oauthToken: String,
             oauthTokenSecret: String) {

    private val client = JumblrClient(consumerKey, consumerSecret)

    init {
        // Create a new client
        client.setToken(oauthToken, oauthTokenSecret)
    }

    fun post(title: String, content: String) {
        val post = client.newPost(blogName, TextPost::class.java)
        post.body = content.replace("\n", "<br>")
        post.title = title
        post.save()
    }
}
