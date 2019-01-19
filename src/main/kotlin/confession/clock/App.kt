package confession.clock

import twitter4j.Query
import twitter4j.Twitter
import twitter4j.TwitterFactory
import java.time.LocalDateTime
import java.util.*

object App {
    val twitter: Twitter = TwitterFactory.getSingleton()

    fun run() {
        val printTweets = {
            val current = LocalDateTime.now()
            val hour = current.hour % 12
            val minutes = current.minute

            val query = """"it's $hour:$minutes am and" OR "it's $hour:${minutes}am and" OR "it's $hour.$minutes am" OR "it's $hour.$minutes am and" """
            for (tweet in twitter.search(Query(query)).tweets) {
                println(tweet.text.replace("\n", ""))
            }
        }
        val task = object: TimerTask() {
            override fun run() { printTweets() }
        }
        Timer().scheduleAtFixedRate(task, 0, 60000)
    }
}

fun main(args: Array<String>) {
    App.run()
}
