package com.example.ktest.utils.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.ktest.utils.extensions.logE
import java.util.StringTokenizer

/**
 * Created by Mr.Developer
 */
class NumberSeparatorTextWatcher(private val view: TextView) : TextWatcher {

    private val isEditText: Boolean = view is EditText

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        try {
            view.removeTextChangedListener(this)
            val value = view.text.toString()


            if (value != "") {

                if (value.startsWith(".")) {
                    view.text = "0."
                }
                if (value.startsWith("0") && !value.startsWith("0.") && value.length > 1) {
                    view.text = value.substring(1)
                }


                val str = view.text.toString().replace(",".toRegex(), "")
                view.text = getDecimalFormattedString(str)
                if (isEditText) {
                    (view as EditText).setSelection(view.text.toString().length)
                }
            }
            view.addTextChangedListener(this)
            return
        } catch (ex: Exception) {
            ex.message?.let { logE(it) }
            view.addTextChangedListener(this)
        }

    }

    companion object {

        fun getDecimalFormattedString(value: String): String {
            val lst = StringTokenizer(value, ".")
            var str1 = value
            var str2 = ""
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken()
                str2 = lst.nextToken()
            }
            var str3 = ""
            var i = 0
            var j = -1 + str1.length
            if (str1[-1 + str1.length] == '.') {
                j--
                str3 = "."
            }
            var k = j
            while (true) {
                if (k < 0) {
                    if (str2.isNotEmpty())
                        str3 = "$str3.$str2"
                    return str3
                }
                if (i == 3) {
                    str3 = ",$str3"
                    i = 0
                }
                str3 = str1[k] + str3
                i++
                k--
            }

        }
    }
}