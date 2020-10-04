package com.nntuan317.yum.ui.auth.sign_in

import com.nntuan317.yum.base.BaseViewState

sealed class SignInViewState : BaseViewState {
    object SignInSuccess : SignInViewState()

    object SignInFailed : SignInViewState()
}