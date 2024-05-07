package com.sameershelar.currencyconverterkmm.core.data

class KmmPreferences(private val prefs: SPref) {

    fun getLong(key: String): Long {
        return prefs.getLong(key)
    }

    fun putLong(key: String, value: Long) {
        prefs.setLong(key,value)
    }
}