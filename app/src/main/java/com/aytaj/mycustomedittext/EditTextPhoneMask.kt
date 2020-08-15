package com.aytaj.mycustomedittext

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText


object EditTextPhoneMask {

    private const val maskforNine = "## ### ## ##"
    private const val maskforTen = "### ### ## ##"

    fun unmask(str: String): String {

        return str.replace("[^0-9]*".toRegex(), "")
    }

    fun insert(editText: EditText): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val str = unmask(s.toString())
                val mask: String
                val defaultMask = getDefaultMask(str)

                Log.e("MYFLAG", "" + str.length)

                mask = when (str.length) {

                    10 -> maskforTen
                    9 -> maskforNine
                    else -> defaultMask
                }
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' &&
                        str.length > old.length ||
                        m != '#' &&
                        str.length < old.length &&
                        str.length != i) {
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                editText.setText(mascara)
                editText.setSelection(mascara.length)
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ){}

            override fun afterTextChanged(s: Editable) {}
        }
    }

    private fun getDefaultMask(str: String): String {
        var defaultMask = maskforNine
        if (str.length > 9) {
            defaultMask = maskforTen
        }
        return defaultMask
    }
}