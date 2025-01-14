package com.jetbrains.kmpapp.presentation.common.utils

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.ui.Modifier

private val tweenSpecFloat = TweenSpec<Float>(100, 100, FastOutLinearInEasing)

fun LazyItemScope.animatedListModifier(): Modifier = Modifier.animateItem(
    fadeInSpec = tweenSpecFloat,
    fadeOutSpec = tweenSpecFloat,
    placementSpec = TweenSpec(100, 100, FastOutLinearInEasing)
)