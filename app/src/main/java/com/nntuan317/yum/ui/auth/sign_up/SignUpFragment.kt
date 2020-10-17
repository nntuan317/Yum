package com.nntuan317.yum.ui.auth.sign_up

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nntuan317.yum.R
import com.nntuan317.yum.base.BaseFragment
import com.nntuan317.yum.databinding.SignUpFragmentBinding
import com.nntuan317.yum.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding, SignUpViewModel>(
    layoutId = R.layout.sign_up_fragment
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.event, ::onViewEventChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    private fun onViewEventChange(viewEvent: SignUpViewEvent) {
        when (viewEvent) {
            SignUpViewEvent.GoBack -> {
                findNavController().popBackStack()
            }
        }
    }

    override fun getViewModelClass(): Class<SignUpViewModel> = SignUpViewModel::class.java

    override fun onInitDataBinding() {
        viewBinding.viewModel = this.viewModel
    }

    private fun onViewStateChange(viewState: SignUpViewState) {
        when (viewState) {
            is SignUpViewState.SignUpSuccess -> {

            }
            is SignUpViewState.SignUpFailed -> {
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