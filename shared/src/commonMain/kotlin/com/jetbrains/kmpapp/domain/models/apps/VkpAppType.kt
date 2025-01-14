package com.jetbrains.kmpapp.domain.models.apps

import kmp_app_template.shared.generated.resources.Res
import kmp_app_template.shared.generated.resources.android
import kmp_app_template.shared.generated.resources.ios
import kmp_app_template.shared.generated.resources.windows
import org.jetbrains.compose.resources.StringResource

enum class VkpAppType(val stringRes: StringResource) {
    ANDROID(Res.string.android),
    IOS(Res.string.ios),
    WINDOWS(Res.string.windows)
}