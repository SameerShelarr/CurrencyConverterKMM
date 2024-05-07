package com.sameershelar.converter.data.local

import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import com.sameershelar.currencyconverterkmm.core.domain.util.CommonFlow
import com.sameershelar.currencyconverterkmm.core.domain.util.toCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCurrencyConversionDataSource : ICurrencyConversionsDataSource {

    private val _conversionRates = MutableStateFlow<List<ConversionRate>>(emptyList())
    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())

    override suspend fun insertConversionRates(rate: ConversionRate) {
        _conversionRates.value += rate
    }

    override fun getLatestConversionRates(): CommonFlow<List<ConversionRate>> {
        return _conversionRates.toCommonFlow()
    }

    override suspend fun insertCurrency(currency: Currency) {
        _currencies.value += currency
    }

    override fun getAllCurrencies(): CommonFlow<List<Currency>> {
        return _currencies.toCommonFlow()
    }

    override suspend fun clearOldConversionRates() {
        _conversionRates.value = emptyList()
    }

    override suspend fun clearOldCurrencies() {
        _currencies.value = emptyList()
    }
}