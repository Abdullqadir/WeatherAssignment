package com.example.weatherassignment.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)

fun String.convertDateToDayOfWeek(): String {
    val today = LocalDate.now()
    return when (val date = LocalDate.parse(this)) {
        today -> {
            "Today, ${date.month.name} ${date.dayOfMonth}"
        }

        today.plusDays(1) -> {
            "Tomorrow"
        }

        else -> {
            "${date.dayOfWeek}"
        }
    }
}