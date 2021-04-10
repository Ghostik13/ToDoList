package com.example.todolist

import android.icu.text.SimpleDateFormat
import java.util.*

class DateCreator(date: Long) {

    val parsing = parsingDate(date)
    val today = dateCreate()

    private fun convertDate(month: String?): String? {
        var monthString: String? = null
        when (month) {
            "01" -> monthString = "Jan"
            "02" -> monthString = "Feb"
            "03" -> monthString = "Mar"
            "04" -> monthString = "Apr"
            "05" -> monthString = "May"
            "06" -> monthString = "Jun"
            "07" -> monthString = "Jul"
            "08" -> monthString = "Aug"
            "09" -> monthString = "Sep"
            "10" -> monthString = "Oct"
            "11" -> monthString = "Nov"
            "12" -> monthString = "Dec"
        }
        return monthString
    }

    private fun parsingDate(date: Long): String? {
        val parsedDate = date.toString().substring(4, date.toString().length)
        var day = parsedDate.substring(2, parsedDate.length)
        if (day.startsWith("0"))
            day = day.replaceFirst("0", "")
        var month: String? = parsedDate.substring(0, 2)
        month = convertDate(month)
        return "$day $month"
    }

    private fun dateCreate(): Long {
        val sdf = SimpleDateFormat("YYYY/MM/dd")
        val date: String = sdf.format(Date())
        val today = date.replace(Regex("[/]+"), "")
        return today.toLong()
    }
}