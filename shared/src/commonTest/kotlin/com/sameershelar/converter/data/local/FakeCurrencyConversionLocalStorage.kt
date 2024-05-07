package com.sameershelar.converter.data.local

import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionLocalStorage

class FakeCurrencyConversionLocalStorage : ICurrencyConversionLocalStorage {

    private var fakeTimeStamp = 0L

    override fun setLastDataSyncTimeStamp(timeStamp: Long) {
        fakeTimeStamp = timeStamp
    }

    override fun getLastDataSyncTimeStamp(): Long {
        return fakeTimeStamp
    }

}