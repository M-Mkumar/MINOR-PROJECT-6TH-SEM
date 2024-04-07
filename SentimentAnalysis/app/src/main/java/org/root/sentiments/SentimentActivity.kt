package org.root.sentiments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import androidx.appcompat.content.res.AppCompatResources
import org.root.sentiments.databinding.ActivitySentimentBinding
import java.lang.NumberFormatException

private const val TAG = "SentimentActivity"
class SentimentActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySentimentBinding
    private lateinit var url: String
    private lateinit var tweeet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: Sentiment Activity")
        binding = ActivitySentimentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var resInt: Int = 2

        url = intent.getStringExtra("url") ?: informInvalidity()

        binding.btnProceed.setOnClickListener {
            tweeet = binding.tweetText.text.toString()
            val response = HttpClient.request(url, tweeet)
            Log.i(TAG, "Response -> $response")
            try {
                resInt = Integer.parseInt(response)
            } catch (ex: NumberFormatException) {
                Log.e(TAG, "Response not in proper format", )
            }

            if (resInt == 1) {
                binding.responseimg.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.happy))
                binding.responsetxt.setText("POSTIVE TWEET")
                binding.responseimg.visibility = VISIBLE
                binding.responsetxt.visibility = VISIBLE
            } else if (resInt == 0) {
                binding.responseimg.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.sad))
                binding.responsetxt.setText("NEGATIVE TWEET")
                binding.responseimg.visibility = android.view.View.VISIBLE
                binding.responsetxt.visibility = android.view.View.VISIBLE
            }
        }
        }

    private fun informInvalidity(): String {
        binding.tweetText.setText("Invalid URL, Can't proceed further")
        binding.btnProceed.isClickable = false
        return ""
    }
}