package com.nntuan317.yum.data.user

import android.net.Uri

data class User(
    val uid: String,
    val name: String?,
    val email: String?,
    val photoUrl: Uri?,
    val isEmailVerified: Boolean
)