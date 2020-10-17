package com.nntuan317.yum.ui.auth.forgot_password

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nntuan317.yum.R
import com.nntuan317.yum.base.BaseFragment
import com.nntuan317.yum.databinding.ForgotPasswordFragmentBinding
import com.nntuan317.yum.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordFragmentBinding, ForgotPasswordViewModel>(
    layoutId = R.layout.forgot_password_fragment
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.event, ::onViewEventChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    override fun getViewModelClass(): Class<ForgotPasswordViewModel> =
        ForgotPasswordViewModel::class.java

    override fun onInitDataBinding() {
        this.viewBinding.viewModel = this.viewModel
    }

    private fun onViewEventChange(event: ForgotPasswordViewEvent) {
        when (event) {
            is ForgotPasswordViewEvent.GoBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun onViewStateChange(viewState: ForgotPasswordViewState) {
        when (viewState) {
            is ForgotPasswordViewState.SendEmailResetPasswordSuccess -> {

            }
            is ForgotPasswordViewState.SendEmailResetPasswordFailed -> {
                showError(viewState.error)
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
        Snackbar.make(requireView(), errorMsg, Snackbar.LENGTH_LONG).show()
    }
}