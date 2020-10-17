package com.nntuan317.yum.ui.auth.forgot_password

sealed class ForgotPasswordViewEvent {
    object GoBack : ForgotPasswordViewEvent()
}