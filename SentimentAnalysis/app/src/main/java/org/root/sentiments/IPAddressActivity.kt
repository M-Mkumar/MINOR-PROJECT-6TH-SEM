package org.root.sentiments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.root.sentiments.databinding.ActivityIpaddressBinding

private const val TAG = "IPAddressActivity"
class IPAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIpaddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIpaddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: IPAdrress Activity")

        binding.btnSubmit.setOnClickListener {
            ip = binding.etIP.text.toString()
            val url = "http://${ip}:5000/predict"
            Intent(this, SentimentActivity::class.java).apply {
                putExtra("url", url)
                startActivity(this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (clicked == 1) {
            binding.etIP.setText(ip)
        }
    }

    companion object {
        var ip: String = ""
        var clicked: Int = 0
    }
}