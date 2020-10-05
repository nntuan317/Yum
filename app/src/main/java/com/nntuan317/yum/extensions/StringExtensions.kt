package com.nntuan317.yum.extensions

import android.util.Patterns

fun String?.isEmailValid() : Boolean {
    return !this.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}