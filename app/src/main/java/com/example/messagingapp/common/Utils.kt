package com.example.messagingapp.common

import android.content.Context
import android.widget.Toast
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Utils {
    private fun Context.makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun showToast(context: Context, message: String) {
        context.makeToast(message)
    }

    internal fun getLocalDateTime(date: Long): LocalDateTime? {
        return Instant.ofEpochSecond(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}