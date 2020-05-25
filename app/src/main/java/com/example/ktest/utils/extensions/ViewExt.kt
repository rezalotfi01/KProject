package com.example.ktest.utils.extensions


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ktest.R
import com.example.ktest.utils.view.NumberSeparatorTextWatcher
import com.example.ktest.utils.view.ViewUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener


fun View.setDirectionLTR() = ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR)


var RecyclerView.isLoadingItems: Boolean
    get() = safeCastAndReturn(getTag(android.R.attr.key), false)
    set(value) = setTag(android.R.attr.key, value)

fun TableLayout.fillTableWithMatrix(squareMatrix: List<List<Int>>) {
    val n: Int = squareMatrix.size
    removeAllViews()
    for (i in 0 until n) {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        for (j in 0 until n) {
            val textView = TextView(context)
            textView.textSize = 15f
            textView.setTextColor(context.getColorCompat(R.color.black))
            textView.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            textView.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            textView.setPadding(ViewUtils.dpToPx(4f).toInt(),ViewUtils.dpToPx(4f).toInt(),ViewUtils.dpToPx(4f).toInt(),ViewUtils.dpToPx(4f).toInt())
            textView.text = squareMatrix[i][j].toString()
            textView.keyListener = null
            row.addView(textView)
        }
        addView(row)
    }
}