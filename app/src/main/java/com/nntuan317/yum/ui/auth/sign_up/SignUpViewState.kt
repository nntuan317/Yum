package com.nntuan317.yum.ui.auth.sign_up

import com.nntuan317.yum.base.BaseViewState

sealed class SignUpViewState : BaseViewState {
    object SignUpSuccess : SignUpViewState()

    data class SignUpFailed(val error: String) : SignUpViewState()
}