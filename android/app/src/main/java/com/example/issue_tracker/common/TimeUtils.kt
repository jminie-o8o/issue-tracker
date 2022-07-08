package com.example.issue_tracker.common

import java.text.SimpleDateFormat
import java.util.*

fun timeStampToDateString(timeStamp: Long): String {
    val pattern = "yyyy-MM-dd"
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    format.isLenient = false
    return format.format(Date(timeStamp))
}