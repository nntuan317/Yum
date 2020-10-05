package com.nntuan317.yum.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun TextInputLayout.showError(error: String?) {
    isErrorEnabled = if (error.isNullOrEmpty()) {
        setError("")
        false
    } else {
        setError(error)
        true
    }
}