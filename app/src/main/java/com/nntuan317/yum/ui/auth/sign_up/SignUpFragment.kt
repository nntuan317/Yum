package com.nntuan317.yum.ui.auth.sign_up

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
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