package com.nntuan317.yum.ui.auth.sign_in

sealed class SignInViewEvent {

    data class SignInWithEmailAndPassword(
        val email: String,
        val password: String
    ) : SignInViewEvent()

    object SignInWithFacebook : SignInViewEvent()

    object SignInWithGoogle : SignInViewEvent()

    object ForgotPassword : SignInViewEvent()

    object SignUp : SignInViewEvent()
}