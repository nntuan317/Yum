package com.nntuan317.yum.ui.auth.forgot_password

import com.nntuan317.yum.base.BaseViewState

sealed class ForgotPasswordViewState : BaseViewState {

    object SendEmailResetPasswordSuccess : ForgotPasswordViewState()

    data class SendEmailResetPasswordFailed(val error: String) : ForgotPasswordViewState()
}