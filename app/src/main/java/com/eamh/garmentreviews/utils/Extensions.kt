package com.eamh.garmentreviews.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormatString(format: String): String{
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}