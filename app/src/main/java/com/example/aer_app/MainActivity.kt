package com.example.aer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        
        setTheme(R.style.Theme_AER_APP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}