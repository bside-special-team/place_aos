package com.special.place.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun commentTime(commentDay: String): Long {
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sf.parse(commentDay)!!
        val today = Calendar.getInstance()
        return (today.time.time - date.time) / (60 * 60 * 24 * 1000)
    }
}