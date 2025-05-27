package io.budge.homeapp.util

import android.util.Log

object Logger {

    fun v(tag: String, message: String) {
        Log.v(tag, message)
    }
}