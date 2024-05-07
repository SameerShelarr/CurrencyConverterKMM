package com.sameershelar.currencyconverterkmm.converter.domain

import com.sameershelar.currencyconverterkmm.converter.data.remote.model.ConversionRatesDTO

interface ICurrencyConversionClient {
    suspend fun getLatestConversionRates(currencies: String): ConversionRatesDTO
    suspend fun getAllCurrencies(): Map<String, String>
}