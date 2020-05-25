package com.example.ktest.utils.view

import android.content.Context
import androidx.annotation.StringRes
import com.example.ktest.R
import com.example.ktest.utils.extensions.format
import com.example.ktest.utils.extensions.resToString
import com.example.ktest.utils.extensions.toStandardDigits

enum class DistanceUnit(val converter: Float, @StringRes val unitRes: Int) {
    CM(2.54f, R.string.centimeter),
    IN(1f, R.string.inch);

    fun getUnitString(valueIN: Float, context: Context) =
        "${(valueIN * converter).format(1).toStandardDigits()} ${unitRes.resToString(context)}"

    fun convert(valueIN: Float) = valueIN * converter
}