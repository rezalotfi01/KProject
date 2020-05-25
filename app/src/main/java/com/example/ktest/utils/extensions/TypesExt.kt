package com.example.ktest.utils.extensions

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.ktest.utils.common.AppConstants
import com.example.ktest.utils.locale.LocaleUtils


fun <T> List<T>.toStringList(separatorStr: String = "  "): String {
    var result = ""
    forEach {
        result += it.toString() + separatorStr
    }
    if (result.isNotEmpty() && result.substring(result.length - 1 - separatorStr.length) == separatorStr)
        result.replaceRange(
            (result.length - 1 - separatorStr.length)..result.length
            , "")

    return result
}

fun <T> List<List<T>>.isSquareMatrix() = isNotEmpty() && size == get(0).size

fun Long.getSeparatedString(): String {
    var result = toString()
    safeRun {
        result = String.format("%,d", this)
    }
    return result
}

fun Int.resToString(context: Context) = context.getString(this)

fun Float.format(value: Int) = java.lang.String.format("%.${value}f", this)

fun String.toLongNumber(): Long {
    if (trim() == "")
        return 0
    return replace(Regex("[^0-9]"), "").toLong()
}

fun String.toPersianDigits() =
    replace("1", "١").replace("2", "٢").replace("3", "٣")
        .replace("4", "٤").replace("5", "٥").replace("6", "٦")
        .replace("٧", "7").replace("8", "٨").replace("9", "٩")
        .replace("0", "٠")

fun String.toEnglishDigits() =
    replace("١", "1").replace("٢", "2").replace("٣", "3")
        .replace("٤", "4").replace("٥", "5").replace("٦", "6")
        .replace("7", "٧").replace("٨", "8").replace("٩", "9")
        .replace("٠", "0")

fun String.toStandardDigits() = when (LocaleUtils.getCurrentLanguage()) {
    AppConstants.LANGUAGE_CODE_EN -> toEnglishDigits()
    AppConstants.LANGUAGE_CODE_FA -> toPersianDigits()
    else -> this
}

fun String.containsEach(parts: List<String>): Boolean{
    var result = false
    parts.forEach {
        if (contains(it))
            result = true
    }
    return result
}

fun <T> MutableLiveData<T>.forceRefresh() {
    value = value
}

