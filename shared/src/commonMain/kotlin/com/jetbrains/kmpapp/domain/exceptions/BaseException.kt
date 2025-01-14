package com.jetbrains.kmpapp.domain.exceptions

import kmp_app_template.shared.generated.resources.Res
import kmp_app_template.shared.generated.resources.default_error
import org.jetbrains.compose.resources.StringResource

abstract class BaseException : Throwable() {
    open val error: StringResource
        get() = Res.string.default_error
}