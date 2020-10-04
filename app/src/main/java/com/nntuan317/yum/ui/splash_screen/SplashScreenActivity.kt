package com.nntuan317.yum.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nntuan317.yum.R
import com.nntuan317.yum.YumActivity
import com.nntuan317.yum.data.user.UserRepository
import com.nntuan317.yum.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIMEOUT = 2_000L

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject lateinit var userRepository: UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(TIMEOUT)
            val user = userRepository.getUser()
            if(user != null) {
                Intent(this@SplashScreenActivity, YumActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                Intent(this@SplashScreenActivity, AuthActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}