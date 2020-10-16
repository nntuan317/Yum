package com.nntuan317.yum.data.user

import android.net.Uri
import kotlinx.serialization.SerialName

data class User(
    @SerialName("uid") val uid: String,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("photo_url") val photoUrl: Uri?,
    @SerialName("is_email_verified") val isEmailVerified: Boolean
)