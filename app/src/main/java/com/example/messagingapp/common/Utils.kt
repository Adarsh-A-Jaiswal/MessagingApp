package com.example.messagingapp.common

import android.content.Context
import android.widget.Toast

object Utils {
    private fun Context.makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun showToast(context: Context, message: String) {
        context.makeToast(message)
    }
}