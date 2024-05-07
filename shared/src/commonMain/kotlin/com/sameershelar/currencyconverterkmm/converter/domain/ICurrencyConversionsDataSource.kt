package com.sameershelar.currencyconverterkmm.converter.domain

import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.core.domain.util.CommonFlow

interface ICurrencyConversionsDataSource {
    suspend fun insertConversionRates(rate: ConversionRate)
    fun getLatestConversionRates(): CommonFlow<List<ConversionRate>>
    suspend fun insertCurrency(currency: Currency)
    fun getAllCurrencies(): CommonFlow<List<Currency>>
    suspend fun clearOldConversionRates()
    suspend fun clearOldCurrencies()
}