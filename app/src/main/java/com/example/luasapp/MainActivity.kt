package com.example.luasapp

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.example.luasapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "Demo APP"
        Log.e("Reading server_url from secret", BuildConfig.SERVER_URL)
    }
}