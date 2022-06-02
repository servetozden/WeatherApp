package com.example.weatherapp_android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.weatherapp_android.MainActivity
import com.example.weatherapp_android.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        openSplashScreen()

    }


    fun openSplashScreen() {
        Handler().postDelayed({
            val i = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(i)
            finish()
        }, SPLASH_SCREEN_TIME_OUT.toLong())
    }
}