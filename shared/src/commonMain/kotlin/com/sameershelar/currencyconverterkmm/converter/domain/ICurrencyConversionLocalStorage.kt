package com.sameershelar.currencyconverterkmm.converter.domain

interface ICurrencyConversionLocalStorage {
    fun setLastDataSyncTimeStamp(timeStamp: Long)
    fun getLastDataSyncTimeStamp(): Long
}