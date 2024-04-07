package org.root.sentiments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.root.sentiments.databinding.ActivitySentimentBinding

private const val TAG = "SentimentActivity"
class SentimentActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySentimentBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: Sentiment Activity")
        binding = ActivitySentimentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.getStringExtra("url") ?: informInvalidity()

        binding.btnProceed.setOnClickListener {
            val response = HttpClient.request(url, binding.tweetText.text.toString())
            Log.i(TAG, "Response -> $response")
        }
    }

    private fun informInvalidity(): String {
        binding.tweetText.setText("Invalid URL, Can't proceed further")
        binding.btnProceed.isClickable = false
        return ""
    }
}