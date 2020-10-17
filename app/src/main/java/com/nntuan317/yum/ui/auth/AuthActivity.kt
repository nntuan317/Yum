package com.nntuan317.yum.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nntuan317.yum.R
import com.nntuan317.yum.ui.auth.sign_in.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
    }
}