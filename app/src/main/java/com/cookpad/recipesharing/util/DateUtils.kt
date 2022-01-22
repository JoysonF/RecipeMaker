package com.cookpad.recipesharing.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getCurrentDate(date: String): String {
        var monthday = "--"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

        val convertedDate: Date?
        try {
            convertedDate = dateFormat.parse(date)
            val sdfmonth = SimpleDateFormat("MM/dd/yy")
            monthday = sdfmonth.format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return monthday
    }
}
