package com.nntuan317.yum.ui.auth.sign_up

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nntuan317.yum.data.user.UserRepository
import com.nntuan317.yum.extensions.isEmailValid
import com.nntuan317.yum.livedata.SingleLiveData
import com.nntuan317.yum.ui.auth.sign_in.SignInViewEvent

class SignUpViewModel  @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val event = SingleLiveData<SignInViewEvent>()
    private val _state = MutableLiveData<SignUpViewState>()
    val state: LiveData<SignUpViewState>
        get() = _state
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()

    fun signUp() {
        var isValid = true
        if (!email.value.isEmailValid()) {
            isValid = false
            emailError.postValue("Email is invalid")
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank() || password.value!!.length < 6) {
            isValid = false
            passwordError.postValue("Password must have more 6 characters")
        } else {
            passwordError.postValue(null)
        }
        if (isValid) {
            val task = userRepository.createUserWithEmailAndPassword(email.value!!, password.value!!)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    _state.postValue(SignUpViewState.SignUpSuccess)
                } else {
                    _state.postValue(SignUpViewState.SignUpFailed("Incorrect email or password"))
                }
            }
        }
    }
}