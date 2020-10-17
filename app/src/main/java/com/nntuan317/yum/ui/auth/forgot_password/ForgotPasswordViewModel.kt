package com.nntuan317.yum.ui.auth.forgot_password

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nntuan317.yum.data.user.UserRepository
import com.nntuan317.yum.extensions.isEmailValid
import com.nntuan317.yum.livedata.SingleLiveData

class ForgotPasswordViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val event = SingleLiveData<ForgotPasswordViewEvent>()
    private val _state = MutableLiveData<ForgotPasswordViewState>()
    val state: LiveData<ForgotPasswordViewState>
        get() = _state
    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()

    fun resetPassword() {
        var isValid = true
        if (!email.value.isEmailValid()) {
            isValid = false
            emailError.postValue("Email is invalid")
        } else {
            emailError.postValue(null)
        }
        if (isValid) {
            userRepository.sendEmailResetPassword(email.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _state.postValue(ForgotPasswordViewState.SendEmailResetPasswordSuccess)
                    } else {
                        _state.postValue(
                            ForgotPasswordViewState.SendEmailResetPasswordFailed(task.exception.toString())
                        )
                    }
                }
        }
    }

    fun goBack() {
        event.postValue(ForgotPasswordViewEvent.GoBack)
    }
}