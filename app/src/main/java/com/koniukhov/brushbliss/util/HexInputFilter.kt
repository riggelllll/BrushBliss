package com.koniukhov.brushbliss.util

import android.text.InputFilter
import android.text.Spanned

class HexInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) return null

        for (i in start until end) {
            val char = source[i]
            if (!char.isDigit() &&
                (char < 'a' || char > 'f') &&
                (char < 'A' || char > 'F')) {
                return ""
            }
        }
        return null
    }
}