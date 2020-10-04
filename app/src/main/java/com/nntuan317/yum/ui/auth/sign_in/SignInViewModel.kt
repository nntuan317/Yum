package com.nntuan317.yum.ui.auth.sign_in

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nntuan317.yum.data.user.UserRepository
import com.nntuan317.yum.livedata.SingleLiveData

class SignInViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val event = SingleLiveData<SignInViewEvent>()
    private val _state = MutableLiveData<SignInViewState>()
    val state: LiveData<SignInViewState>
        get() = _state

    fun signInWithGoogle() {
        event.postValue(SignInViewEvent.SignInWithGoogle)
    }

    fun handleSignInWithGoogleResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val task = userRepository.signInWithGoogle(idToken)
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                _state.postValue(SignInViewState.SignInSuccess)
            } else {
                _state.postValue(SignInViewState.SignInSuccess)
            }
        }
    }
}