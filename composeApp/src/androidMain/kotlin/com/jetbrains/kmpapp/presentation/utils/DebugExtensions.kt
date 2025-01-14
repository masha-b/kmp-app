package com.jetbrains.kmpapp.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.jetbrains.kmpapp.isDebug
import com.jetbrains.kmpapp.presentation.base.Reducer
import com.jetbrains.kmpapp.presentation.base.TimeCapsule
import com.jetbrains.kmpapp.R


fun Modifier.debugInputPointer(
    context: Context,
    timeTravelCapsule: TimeCapsule<out Reducer.ViewState>,
): Modifier {
    return if (isDebug) {
        this.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    showDebugAlertDialog(context, timeTravelCapsule)
                }
            )
        }
    } else this
}

private fun showDebugAlertDialog(
    context: Context,
    timeTravelCapsule: TimeCapsule<out Reducer.ViewState>,
) {
    val alertDialogBuilder = AlertDialog.Builder(context, R.style.DebugDialogTheme)
    val adapter = ArrayAdapter<DebugState>(context, R.layout.debug_menu_item)

    adapter.addAll(timeTravelCapsule.getStates().reversed().mapIndexed(::DebugState))
    alertDialogBuilder.setAdapter(adapter) { dialog, which ->
        timeTravelCapsule.selectState(which)
    }

    alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
        dialog.dismiss()
    }

    val dialog = alertDialogBuilder.create()
    dialog.show()
}

private data class DebugState(val index: Int, val state: Reducer.ViewState) {
    override fun toString(): String {
        return "${index + 1}. $state"
    }
}