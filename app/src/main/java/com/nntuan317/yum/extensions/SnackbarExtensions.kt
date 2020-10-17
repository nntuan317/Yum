package com.nntuan317.yum.extensions

import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun Snackbar.setMaxLine(maxLine: Int): Snackbar {
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = maxLine
    return this
}