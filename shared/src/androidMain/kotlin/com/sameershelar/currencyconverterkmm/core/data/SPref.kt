package com.sameershelar.currencyconverterkmm.core.data

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

actual typealias SPref = Application

actual fun SPref.getLong(key: String): Long {
    val prefs: SharedPreferences = this.getSharedPreferences("", MODE_PRIVATE)
    return prefs.getLong(key, 0L)
}

actual fun SPref.setLong(key: String, value: Long) {
    val prefs: SharedPreferences = this.getSharedPreferences("", MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putLong(key,value)
    editor.apply()
}