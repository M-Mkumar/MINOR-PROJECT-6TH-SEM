package org.root.sentiments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.root.sentiments.databinding.ActivityIpAddressBinding

class IpAddressActivity : AppCompatActivity() {
    public var httpUrl = "";

    private lateinit var binding: ActivityIpAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIpAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submit.setOnClickListener {
            httpUrl = BASE_ADDRESS + binding.etIp.text.toString()
        }

    }

    companion object{
        const val BASE_ADDRESS = "http://"
    }
}