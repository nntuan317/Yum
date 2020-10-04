package com.nntuan317.yum.ui.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nntuan317.yum.R
import com.nntuan317.yum.YumActivity
import com.nntuan317.yum.base.BaseFragment
import com.nntuan317.yum.databinding.SignInFragmentBinding
import com.nntuan317.yum.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import javax.inject.Inject

private const val RC_SIGN_IN_WITH_GOOGLE = 1001

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding, SignInViewModel>(
    layoutId = R.layout.sign_in_fragment
) {

    companion object {
        fun newInstance() = SignInFragment()
    }

    @Inject
    lateinit var googleSignIn: GoogleSignInClient

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.event, ::onViewEventChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleSignInWithGoogleResult(task)
        }
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = this.viewModel
        viewBinding.btnSignInWithGoogle.setOnClickListener {
            viewModel.signInWithGoogle()
        }
    }

    override fun getViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java

    private fun onViewEventChange(event: SignInViewEvent) {
        when (event) {
            is SignInViewEvent.SignInWithGoogle -> {
                signInWithGoogle()
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
                showError()
            }
        }
    }

    private fun showError() {
        MotionToast.createToast(
            requireActivity(),
            "Sign in failed",
            MotionToast.TOAST_ERROR,
            MotionToast.GRAVITY_CENTER,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), R.font.helvetica_regular)
        )
    }
}