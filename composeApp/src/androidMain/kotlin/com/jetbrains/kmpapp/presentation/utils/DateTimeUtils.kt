package com.jetbrains.kmpapp.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val BUILD_DATE_FORMAT = "dd.MM.yyyy HH:mm"

@SuppressLint("SimpleDateFormat")
fun formatBuildDatetime(datetime: Long): String {
    return SimpleDateFormat(
        BUILD_DATE_FORMAT,
        Locale("ru", "RU")
    ).format(Date(datetime * 1000))
}