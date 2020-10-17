package com.nntuan317.yum.bindings

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nntuan317.yum.R

@BindingAdapter("error")
fun CheckBox.showError(error: String?) {
    if (error.isNullOrEmpty()) {
        this.buttonTintList = null
    } else {
        this.buttonTintList = ColorStateList.valueOf(Color.RED)
    }
}