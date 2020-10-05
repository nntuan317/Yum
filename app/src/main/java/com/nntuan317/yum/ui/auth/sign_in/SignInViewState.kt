package com.nntuan317.yum.ui.auth.sign_in

import com.nntuan317.yum.base.BaseViewState

sealed class SignInViewState : BaseViewState {
    object EmailInValid : SignInViewState()

    object PasswordInvalid : SignInViewState()

    object SignInSuccess : SignInViewState()

    data class SignInFailed(val errorMsg: String) : SignInViewState()
}