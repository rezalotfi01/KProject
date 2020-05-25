package com.example.ktest.utils.view

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

object ViewUtils {

    fun dpToPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

    fun mmToPx(mm: Float, coefficient: Float, displayMetrics: DisplayMetrics): Float {
        return mm * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, coefficient, displayMetrics)
    }

    fun pxToIn(px: Float, coefficient: Float, displayMetrics: DisplayMetrics): Float {
        return px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, coefficient, displayMetrics)
    }
}