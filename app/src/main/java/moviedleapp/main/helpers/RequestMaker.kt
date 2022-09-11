package moviedleapp.main.helpers

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RequestMaker {
    companion object {
        fun makeGETRequest(client: OkHttpClient, requestUrl: String): String {
            val request = Request.Builder()
                .url(requestUrl)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                return response.body!!.string()
            }
        }
    }
}