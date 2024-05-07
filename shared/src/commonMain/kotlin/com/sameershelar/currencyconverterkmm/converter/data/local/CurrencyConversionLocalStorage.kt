package com.sameershelar.currencyconverterkmm.converter.data.local

import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.core.data.KmmPreferences

class CurrencyConversionLocalStorage(
    private val prefs: KmmPreferences
) : ICurrencyConversionLocalStorage {

    companion object {
        const val LAST_SYNC_TIME_STAMP = "last_sync_time_stamp"
    }

    override fun setLastDataSyncTimeStamp(timeStamp: Long) {
        prefs.putLong(
            key = LAST_SYNC_TIME_STAMP,
            value = timeStamp
        )
    }

    override fun getLastDataSyncTimeStamp(): Long {
        return prefs.getLong(LAST_SYNC_TIME_STAMP)
    }
}