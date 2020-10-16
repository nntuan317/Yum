package com.nntuan317.yum.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp:Float) : Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}