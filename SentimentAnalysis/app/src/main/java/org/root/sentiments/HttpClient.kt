package org.root.sentiments

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HttpClient {

    companion object {
        private var client = OkHttpClient()

        @Throws(IOException::class)
        fun request(url: String?, tweet: String): String {
            var result: String = ""
             val formBody = FormBody.Builder()
                    .add("tweet", tweet)
                    .build()
                val request: Request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()
            try {
            val bgThread: Thread = Thread {
                    result = client.newCall(request).execute().body().string()
                }
                bgThread.start()
                bgThread.join()

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return result
        }
    }
}