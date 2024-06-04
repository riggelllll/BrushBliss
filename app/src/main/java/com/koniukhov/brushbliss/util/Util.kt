package com.koniukhov.brushbliss.util

import android.graphics.Color

object Util {
    fun hexToRgb(hex: String): Int? {
        if (hex.length != HEX_LENGTH || !hex.matches(Regex("[0-9A-Fa-f]{$HEX_LENGTH}"))){
            return null
        }

        val red = hex.substring(0, 2).toInt(HEXADECIMAL_RADIX)
        val green = hex.substring(2, 4).toInt(HEXADECIMAL_RADIX)
        val blue = hex.substring(4, 6).toInt(HEXADECIMAL_RADIX)

        return Color.rgb(red, green, blue)
    }
}