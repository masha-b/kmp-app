package com.jetbrains.kmpapp.utils

import kotlin.text.isNotBlank

fun String.isEmailValid(): Boolean {
    return isNotBlank() && matches(Regex(emailAddressPattern))
}

private const val emailAddressPattern =
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"