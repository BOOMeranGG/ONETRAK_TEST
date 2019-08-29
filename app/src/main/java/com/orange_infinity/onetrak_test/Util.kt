package com.orange_infinity.onetrak_test

import android.content.res.Resources
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*

fun convertToPx(dp: Int, resources: Resources): Int {
    val dm = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), dm).toInt()
}

fun getDateFromTimeMilli(milliSeconds: Long, dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}