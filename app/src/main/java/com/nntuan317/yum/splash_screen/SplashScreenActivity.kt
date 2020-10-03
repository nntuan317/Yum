package com.nntuan317.yum.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nntuan317.yum.R
import com.nntuan317.yum.YumActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TIMEOUT = 2_000L

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(TIMEOUT)
            Intent(this@SplashScreenActivity, YumActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}