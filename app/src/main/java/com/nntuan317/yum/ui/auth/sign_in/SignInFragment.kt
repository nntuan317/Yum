package com.nntuan317.yum.ui.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.snackbar.Snackbar
import com.nntuan317.yum.R
import com.nntuan317.yum.YumActivity
import com.nntuan317.yum.base.BaseFragment
import com.nntuan317.yum.databinding.SignInFragmentBinding
import com.nntuan317.yum.extensions.observe
import com.nntuan317.yum.extensions.setMaxLine
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


private const val RC_SIGN_IN_WITH_GOOGLE = 1001

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding, SignInViewModel>(
    layoutId = R.layout.sign_in_fragment
) {
    @Inject
    lateinit var googleSignIn: GoogleSignInClient

    private var callbackManager = CallbackManager.Factory.create()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.event, ::onViewEventChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    override fun onStart() {
        super.onStart()
        registerFacebookCallback()
    }

    override fun onStop() {
        super.onStop()
        unregisterFacebookCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleSignInWithGoogleResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = this.viewModel
    }

    override fun getViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java

    private fun registerFacebookCallback() {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    viewModel.handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Timber.d("facebook:onCancel")
                }

                override fun onError(error: FacebookException?) {
                    Timber.e("facebook:onError $error")
                    showError(error.toString())
                }
            })
    }

    private fun unregisterFacebookCallback() {
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    private fun onViewEventChange(event: SignInViewEvent) {
        when (event) {
            is SignInViewEvent.SignInWithGoogle -> {
                signInWithGoogle()
            }
            is SignInViewEvent.SignInWithFacebook -> {
                LoginManager.getInstance().logInWithReadPermissions(
                    this, arrayListOf("email", "public_profile")
                )
            }
            is SignInViewEvent.ForgotPassword -> {
                findNavController().navigate(R.id.action_forgot_password)
            }
            is SignInViewEvent.SignUp -> {
                findNavController().navigate(R.id.action_sign_up)
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignIn.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN_WITH_GOOGLE)
    }

    private fun onViewStateChange(viewState: SignInViewState) {
        when (viewState) {
            is SignInViewState.SignInSuccess -> {
                Intent(requireActivity(), YumActivity::class.java).also {
                    startActivity(it)
                    activity?.finish()
                }
            }
            is SignInViewState.SignInFailed -> {
                showError(viewState.errorMsg)
            }
        }
    }

    private fun showError(errorMsg: String) {
        // Bug lib here
//        MotionToast.createToast(requireActivity(),"Sign in failed!",
//            MotionToast.TOAST_ERROR,
//            MotionToast.GRAVITY_BOTTOM,
//            MotionToast.LONG_DURATION,
//            ResourcesCompat.getFont(requireContext(),R.font.helvetica_regular))
        Snackbar.make(requireView(), errorMsg, Snackbar.LENGTH_LONG).setMaxLine(5).show()
    }
}